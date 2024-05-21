package com.finalproject.azercell.controller;


import com.finalproject.azercell.model.BalanceHistoryDto;
import com.finalproject.azercell.service.BalanceHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/balance-history")
@RequiredArgsConstructor
public class BalanceHistoryController {

    private final BalanceHistoryService balanceHistoryService;
    @GetMapping("/all")
    public List<BalanceHistoryDto> getAllForNumber(HttpServletRequest request) {
        return balanceHistoryService.getAllForNumber(request);
    }

    @GetMapping("/{id}")
    public BalanceHistoryDto get(@PathVariable Integer id) {
        return balanceHistoryService.get(id);
    }

}
