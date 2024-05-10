package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.mapper.BalanceHistoryMapper;
import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.repository.BalanceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceHistoryRepository balanceHistoryRepository;
    private final BalanceHistoryMapper balanceHistoryMapper;

    public void save(BalanceHistoryEntity balanceHistoryEntity){
        balanceHistoryRepository.save(balanceHistoryEntity);
    }

    public List<BalanceHistoryDto> get(Integer userId){

        List<BalanceHistoryDto> balanceHistory = balanceHistoryRepository.findByUserId(userId);
        Collections.sort(balanceHistory, Comparator.comparing(BalanceHistoryDto::getDateTime));
        return balanceHistory;
    }

    public List<BalanceHistoryDto> getAll(){
        return balanceHistoryRepository.findAll().stream().map(e -> balanceHistoryMapper.mapToDto((BalanceHistoryEntity) e)).toList();
    }
}
