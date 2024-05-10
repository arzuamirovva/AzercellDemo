package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {


    CardResponseDto mapToDto(CardEntity cardEntity);
    CardEntity mapToEntity(CardRequestDto cardRequestDto);
}
