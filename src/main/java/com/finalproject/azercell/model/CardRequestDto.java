package com.finalproject.azercell.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequestDto {
    @Size(min = 16,max=16)
    @NotNull
    private String number;

    @Size(min=3,max=3)
    @NotNull
    private String CVV;

    @Future
    @NotNull
    private LocalDate expDate;

    private Integer userId;
}
