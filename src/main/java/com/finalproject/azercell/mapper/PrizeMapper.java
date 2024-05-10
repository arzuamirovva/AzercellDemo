package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.PrizeEntity;
import com.finalproject.azercell.model.PrizeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrizeMapper {

    PrizeDto mapToDto(PrizeEntity prizeEntity);
    PrizeEntity mapToEntity(PrizeDto prizeDto);
}
