package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.PassportEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.exception.NotFoundException;
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

    public void update(Integer id, UserRequestDto userRequestDto){
        log.info("ActionLog.UserService.update has started");

        if (!userRepository.existsById(id)){
            throw new NotFoundException("Not Found User");
        }
        var user = userMapper.mapToEntity(userRequestDto);
        user.setId(id);
        userRepository.save(user);
        log.info("ActionLog.UserService.update has ended");

    }

    public List getAll(){
        log.info("ActionLog.UserService.getAll has started");

        List list = userRepository.findAll().stream().map(e -> userMapper.mapToDto((UserEntity) e)).toList();

        log.info("ActionLog.UserService.getAll has ended");
        return list;
    }

    public UserResponseDto get(Integer id){
        log.info("ActionLog.UserService.get has started");

        UserResponseDto dto = userMapper.mapToDto(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        ));
        log.info("ActionLog.UserService.get has ended");

        return dto;
    }

    public void delete(Integer id){
        log.info("ActionLog.UserService.delete has started");

        if (!userRepository.existsById(id)){
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(id);
        log.info("ActionLog.UserService.delete has ended");

    }
}
