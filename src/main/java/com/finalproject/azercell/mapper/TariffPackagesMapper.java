package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.TariffPackageEntity;
import com.finalproject.azercell.model.TariffPackagesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TariffPackagesMapper {

    TariffPackagesDto mapToDto(TariffPackageEntity tariffPackageEntity);
    TariffPackageEntity mapToEntity(TariffPackagesDto tariffPackagesDto);
}
