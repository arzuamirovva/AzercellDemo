package com.finalproject.azercell.mapper;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.model.BalanceHistoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BalanceHistoryMapper {

    BalanceHistoryDto mapToDto(BalanceHistoryEntity balanceHistoryEntity);

    BalanceHistoryEntity mapToEntity(BalanceHistoryDto balanceHistoryDto);

}
