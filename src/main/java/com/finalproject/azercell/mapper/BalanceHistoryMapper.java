package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.model.BalanceHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceHistoryMapper {

    @Mapping(source = "number.number", target = "number")
    BalanceHistoryDto mapToDto(BalanceHistoryEntity balanceHistoryEntity);
    @Mapping(source = "number", target = "number.number")
    BalanceHistoryEntity mapToEntity(BalanceHistoryDto balanceHistoryDto);

}
