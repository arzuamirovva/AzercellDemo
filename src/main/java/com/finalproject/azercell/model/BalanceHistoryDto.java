package com.finalproject.azercell.model;

import com.finalproject.azercell.entity.NumberEntity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistoryDto {

    private Integer id;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", message = "Amount must be greater than or equal to 0")
    private Double amount;

    @NotNull(message = "Date time cannot be null")
    private LocalDateTime transactionDate;
    private String number;
    private Integer userId;
}
