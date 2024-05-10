package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.AppEntity;
import com.finalproject.azercell.model.AppDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppMapper {

    AppEntity mapToEntity(AppDto appDto);

    AppDto mapToDto(AppEntity appEntity);
}
