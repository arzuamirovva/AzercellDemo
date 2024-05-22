package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.model.NumberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NumberMapper {

    @Mapping(source = "userId", target = "user.id")
    NumberEntity mapToEntity(NumberDto numberDto);

    @Mapping(source = "user.id", target = "userId")
    NumberDto mapToDto(NumberEntity numberEntity);


}
