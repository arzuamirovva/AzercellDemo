package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.service.BalanceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balance-history")
@RequiredArgsConstructor
public class BalanceHistoryController {

    private final BalanceHistoryService balanceHistoryService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BalanceHistoryDto> get(@PathVariable Integer userId){
        return balanceHistoryService.get(userId);
    }

}
