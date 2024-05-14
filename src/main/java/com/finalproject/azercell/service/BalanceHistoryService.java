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

    public void save(BalanceHistoryEntity balanceHistoryEntity)
    {
        log.info("ActionLog.BalanceHistoryService.save has started");
        balanceHistoryRepository.save(balanceHistoryEntity);
        log.info("ActionLog.BalanceHistoryService.save has ended");

    }

    public List<BalanceHistoryDto> get(Integer userId){
        log.info("ActionLog.BalanceHistoryService.get has started");

        List<BalanceHistoryDto> balanceHistory = balanceHistoryRepository.findByUserId(userId);
        Collections.sort(balanceHistory, Comparator.comparing(BalanceHistoryDto::getDateTime));
        log.info("ActionLog.BalanceHistoryService.get has ended");
        return balanceHistory;
    }

    public List<BalanceHistoryDto> getAll(){
        log.info("ActionLog.BalanceHistoryService.getAll has started");

        List<BalanceHistoryDto> balanceHistoryDtoList = balanceHistoryRepository.findAll().stream().map(e -> balanceHistoryMapper.mapToDto((BalanceHistoryEntity) e)).toList();
        log.info("ActionLog.BalanceHistoryService.getAll has ended");

        return balanceHistoryDtoList;
    }
}
