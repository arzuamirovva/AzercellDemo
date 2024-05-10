package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.model.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapToEntity(UserRequestDto userRequestDto);
    UserResponseDto mapToDto(UserEntity userEntity);

}
