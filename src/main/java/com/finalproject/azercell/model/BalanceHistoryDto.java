package com.finalproject.azercell.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistoryDto {

    private Integer id;
    private Double amount;
    private LocalDateTime dateTime;
}
