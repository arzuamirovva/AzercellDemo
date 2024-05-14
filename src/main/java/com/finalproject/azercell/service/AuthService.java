package com.finalproject.azercell.service;
import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.PassportEntity;
import com.finalproject.azercell.entity.UserEntity;

import com.finalproject.azercell.enums.RoleEnum;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.exception.UserAlreadyExist;
import com.finalproject.azercell.mapper.UserMapper;
import com.finalproject.azercell.model.LoginRequestDto;

import com.finalproject.azercell.model.LoginResponseDto;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.PassportRepository;
import com.finalproject.azercell.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final NumberRepository numberRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final PassportRepository passportRepository;
    private final UserMapper userMapper;



    public void register(UserRequestDto dto){
        log.info("ActionLog.AuthService.register method has started");
        PassportEntity passport = (PassportEntity) passportRepository.findByFin(dto.getFin()).orElseThrow();
        if (userRepository.findByPassport(passport).isPresent()){
            throw new UserAlreadyExist("User Already Exist");
        }
        String number = dto.getNumber();
        if (numberRepository.findByNumber(number).isEmpty()){
            throw new NotFoundException("THIS_NUMBER_NOT_FOUND");
        }
        UserEntity entity = userMapper.mapToEntity(dto);
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setPassport((PassportEntity) passportRepository.findByFin(dto.getFin()).get()); //duzelt
        entity.setRole(RoleEnum.CUSTOMER);
        userRepository.save(entity);
        var numberEntity = numberRepository.findByNumber(dto.getNumber()).orElseThrow(() -> new NotFoundException("Number Not Found"));
        numberEntity.setUser(entity);
        numberEntity.setPassword(entity.getPassword());
        numberRepository.save(numberEntity);
        log.info("ActionLog.UserService.create has ended");
    }

    public LoginResponseDto login(LoginRequestDto request){
        log.info("Started");
        NumberEntity numberEntity = numberRepository.findByNumber(request.getNumber()).orElseThrow();
        System.out.println(numberEntity.getNumber());
        UserEntity user = numberEntity.getUser();
        log.info(user.getFullName());

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNumber(), request.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            NumberEntity numberEntity1 = numberRepository.findByNumber(request.getNumber()).orElseThrow();

            String token = jwtUtil.createToken(numberEntity1);

            LoginResponseDto response = new LoginResponseDto(username,token);
            log.info("Ended");
            return response;
//            return ResponseEntity.status(HttpStatus.OK).header("userId", String.valueOf(entity.getId())).body(response);
        }catch (BadCredentialsException e){
            throw new RuntimeException("Invalid Username or password");
        }catch (Throwable e){
            throw new RuntimeException(e.getMessage());
        }
    }



}