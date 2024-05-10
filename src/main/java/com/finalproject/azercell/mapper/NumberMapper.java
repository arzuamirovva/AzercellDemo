package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.model.NumberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NumberMapper {

    NumberEntity mapToEntity(NumberDto numberDto);
    NumberDto mapToDto(NumberEntity numberEntity);
}
