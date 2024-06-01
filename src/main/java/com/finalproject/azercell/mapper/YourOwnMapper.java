package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.YourOwnTariffEntity;
import com.finalproject.azercell.model.YourOwnDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface YourOwnMapper {
    YourOwnDto mapToDto(YourOwnTariffEntity yourOwnTariffEntity);
    YourOwnTariffEntity mapToEntity(YourOwnDto yourOwnDto);
}
