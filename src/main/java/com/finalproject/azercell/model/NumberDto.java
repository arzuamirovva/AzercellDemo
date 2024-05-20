package com.finalproject.azercell.model;

import com.finalproject.azercell.enums.NumberStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberDto {

    private Integer id;

    @NotBlank(message = "Number cannot be blank")
    private String number;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Status cannot be null")
    private NumberStatus status;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance cannot be negative")
    private Double balance;

    @NotNull(message = "Internet balance cannot be null")
    @PositiveOrZero(message = "Internet balance cannot be negative")
    private Double internetBalance;

    @NotNull(message = "Minute balance cannot be null")
    @PositiveOrZero(message = "Minute balance cannot be negative")
    private Integer minuteBalance;

    @NotNull(message = "SMS balance cannot be null")
    @PositiveOrZero(message = "SMS balance cannot be negative")
    private Integer smsBalance;

    private LocalDateTime lastSpinTime;
    private Boolean hasChance;
    private Integer freeMinutes = 0;
    private Integer freeInternet = 0;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be positive")
    private Integer userId;

    @NotNull(message = "Tariff ID cannot be null")
    @Positive(message = "Tariff ID must be positive")
    private Integer tariffId;
}
