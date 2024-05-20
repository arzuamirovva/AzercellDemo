package com.finalproject.azercell.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequestDto {
    @NotNull(message = "Card number cannot be null")
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    private String number;

    @NotNull(message = "CVV cannot be null")
    @Size(min = 3, max = 3, message = "CVV must be exactly 3 characters")
    private String CVV;

    @NotNull(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    private LocalDate expDate;

    private Integer userId;
}
