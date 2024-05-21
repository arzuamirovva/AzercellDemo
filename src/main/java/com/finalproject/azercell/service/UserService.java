package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.UserMapper;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.model.UserResponseDto;
import com.finalproject.azercell.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void update(Integer id, UserRequestDto userRequestDto){
        log.info("ActionLog.UserService.update has started for user {}", id);

        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User Not found"));

        UserEntity user = userMapper.mapToEntity(userRequestDto);
        user.setId(id);
        user.setRole(userEntity.getRole());
        user.setPassport(userEntity.getPassport());
        user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(user);
        log.info("ActionLog.UserService.update has ended for user {}", id);

    }

    public List<UserResponseDto> getAll(){
        log.info("ActionLog.UserService.getAll has started");

        List<UserResponseDto> list = userRepository.findAll().stream().map(userMapper::mapToDto).toList();

        log.info("ActionLog.UserService.getAll has ended");
        return list;
    }

    public UserResponseDto getDetails(HttpServletRequest request){
        Integer id = jwtUtil.getUserId(jwtUtil.resolveClaims(request));

        log.info("ActionLog.UserService.get has started for user {}", id );
        UserResponseDto dto = userMapper.mapToDto(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        ));
        log.info("ActionLog.UserService.get has ended for user {}", id);
        return dto;
    }

    public UserResponseDto get(Integer id){
        log.info("ActionLog.UserService.get has started for user {}", id );
        UserResponseDto dto = userMapper.mapToDto(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        ));
        log.info("ActionLog.UserService.get has ended for user {}", id);
        return dto;
    }

    @Transactional
    public void delete(Integer id){
        log.info("ActionLog.UserService.delete has started for user {}", id);

        if (!userRepository.existsById(id)){
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
        log.info("ActionLog.UserService.delete has ended for user {}", id);
    }

    public void deleteOwn(HttpServletRequest request){
        Integer id = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.UserService.delete has started for user {}", id);

        if (!userRepository.existsById(id)){
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
        log.info("ActionLog.UserService.delete has ended for user {}", id);
    }
}
