package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.PassportEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.mapper.UserMapper;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.model.UserResponseDto;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.PassportRepository;
import com.finalproject.azercell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NumberRepository numberRepository;
    private final PassportRepository passportRepository;
    public void create(UserRequestDto userRequestDto){
        log.info("Create Method has started for {}", userRequestDto.getFin());
        if (passportRepository.findByFin(userRequestDto.getFin()).isEmpty()){
            throw new RuntimeException("Invalid Fin");
        }


        String number = userRequestDto.getNumber();
        if (numberRepository.findByNumber(number).isEmpty()){
            throw new RuntimeException("THIS_NUMBER_NOT_FOUND");
        }
        UserEntity entity = userMapper.mapToEntity(userRequestDto);
        entity.setPassport((PassportEntity) passportRepository.findByFin(userRequestDto.getFin()).get());
        userRepository.save(entity);
        var numberEntity = numberRepository.findByNumber(userRequestDto.getNumber()).get();
        numberEntity.setUser(entity);
        numberRepository.save(numberEntity);
        log.info("Create Method has finished for {}", userRequestDto.getFin());

    }

    public void update(Integer id, UserRequestDto userRequestDto){
        if (!userRepository.existsById(id)){
            throw new RuntimeException("Not Found User");
        }
        var user = userMapper.mapToEntity(userRequestDto);
        user.setId(id);
        userRepository.save(user);
    }

    public List<UserResponseDto> getAll(){
        return userRepository.findAll().stream().map( e -> userMapper.mapToDto((UserEntity) e)).toList();
    }

    public UserResponseDto get(Integer id){
        return userMapper.mapToDto(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("USER_NOT_FOUND")
        ));
    }

    public void delete(Integer id){
        if (!userRepository.existsById(id)){
            throw new RuntimeException("User Not Found");
        }
        userRepository.deleteById(id);
    }


}
