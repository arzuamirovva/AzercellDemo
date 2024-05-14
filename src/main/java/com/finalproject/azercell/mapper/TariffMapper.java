package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.model.TariffDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TariffMapper {

    TariffEntity mapToEntity(TariffDto tariffDto);

    TariffDto mapToDto(TariffEntity tariffEntity);
}
