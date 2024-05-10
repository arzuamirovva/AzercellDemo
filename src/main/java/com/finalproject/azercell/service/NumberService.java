package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.mapper.NumberMapper;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.repository.BalanceHistoryRepository;
import com.finalproject.azercell.repository.NumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberService {

    private final NumberMapper numberMapper;
    private final NumberRepository numberRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;


    public List<NumberDto> getAll(){
        return numberRepository.findAll().stream().map(e -> numberMapper.mapToDto((NumberEntity) e)).toList();
    }

    public NumberDto get(Integer id){
        return numberMapper.mapToDto(numberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("NUMBER_NOT_FOUND"))
        );
    }

    public void deactivateNumber(NumberEntity numberEntity){
        LocalDateTime lastTransactionDate = balanceHistoryRepository.findLastTransactionDateByNumber(numberEntity).get();
        if (lastTransactionDate.isBefore(LocalDateTime.now().minusMonths(3))) {
            numberEntity.setStatus(NumberStatus.TWO_SIDED_DEACTIVATED);
        }else if(lastTransactionDate.isBefore(LocalDateTime.now().minusMonths(1))){
            numberEntity.setStatus(NumberStatus.ONE_SIDED_DEACTIVATED);
        }
        numberRepository.save(numberEntity);
    }
}
