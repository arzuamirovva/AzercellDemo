package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.service.BalanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/balance-history")
@RequiredArgsConstructor
public class BalanceHistoryController {

    private final BalanceHistoryService balanceHistoryService;

    @GetMapping("/{userId}")
    public List<BalanceHistoryDto> get(@PathVariable Integer userId){
        return balanceHistoryService.get(userId);
    }
}
