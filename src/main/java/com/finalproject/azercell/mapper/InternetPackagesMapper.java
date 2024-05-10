package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.InternetPackageEntity;
import com.finalproject.azercell.model.InternetPackagesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternetPackagesMapper {

    InternetPackageEntity mapToEntity(InternetPackagesDto internetPackagesDto);

    InternetPackagesDto mapToDto(InternetPackageEntity internetPackageEntity);
}
