package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.service.BalanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/balance-history")
@RequiredArgsConstructor
public class BalanceHistoryController {

    private final BalanceHistoryService balanceHistoryService;
    @GetMapping("/all/{numberId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BalanceHistoryDto> getAllForNumber(@PathVariable Integer numberId) {
        return balanceHistoryService.getAllForNumber(numberId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BalanceHistoryDto get(@PathVariable Integer id) {
        return balanceHistoryService.get(id);
    }

}
