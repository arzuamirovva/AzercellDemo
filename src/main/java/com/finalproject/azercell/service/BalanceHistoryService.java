package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.BalanceHistoryMapper;
import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.repository.BalanceHistoryRepository;
import com.finalproject.azercell.repository.NumberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceHistoryService {

    private final BalanceHistoryRepository balanceHistoryRepository;
    private final NumberRepository numberRepository;
    private final BalanceHistoryMapper balanceHistoryMapper;
    private final JwtUtil jwtUtil;

    public void addBalanceHistory(Integer numberId, Double amount) {
        log.info("ActionLog.BalanceHistoryService.addBalanceHistory has started for numberId: {} with amount: {}", numberId, amount);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));

        BalanceHistoryEntity balanceHistory = new BalanceHistoryEntity();
        balanceHistory.setAmount(amount);
        balanceHistory.setTransactionDate(LocalDateTime.now());
        balanceHistory.setNumber(number);
        balanceHistory.setUserId(number.getUser().getId());

        balanceHistoryRepository.save(balanceHistory);

        log.info("ActionLog.BalanceHistoryService.addBalanceHistory has ended for numberId: {} with amount: {}", numberId, amount);
    }


    public List<BalanceHistoryDto> getAllForNumber(HttpServletRequest request) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.BalanceHistoryService.getForNumber has started for numberId: {}", numberId);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));

        List<BalanceHistoryDto> balanceHistoryList = balanceHistoryRepository.findAllByNumber(number).stream().map(balanceHistoryMapper::mapToDto).toList();

        log.info("ActionLog.BalanceHistoryService.getForNumber has ended for numberId: {}", numberId);
        return balanceHistoryList;
    }

    public BalanceHistoryDto get(Integer id) {
        log.info("ActionLog.BalanceHistoryService.get has started for balanceHistoryId: {}", id);

        BalanceHistoryEntity balanceHistoryEntity = balanceHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Balance history not found with id: " + id));

        BalanceHistoryDto balanceHistoryDto = balanceHistoryMapper.mapToDto(balanceHistoryEntity);
        log.info("ActionLog.BalanceHistoryService.get has ended for balanceHistoryId: {}", id);
        return balanceHistoryDto;
    }
}
