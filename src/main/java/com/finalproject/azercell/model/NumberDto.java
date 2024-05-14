package com.finalproject.azercell.model;

import com.finalproject.azercell.enums.NumberStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberDto {

    private Integer id;
    private String number;
    private String password;
    private NumberStatus status;

    private Double balance;
    private Double internetBalance;
    private Integer minuteBalance;
    private Integer smsBalance;

    private LocalDateTime lastSpinTime;
    private Boolean hasChance;
    private Integer freeMinutes=0;
    private Integer freeInternet=0;
    private Integer userId;
    private Integer tariffId;
    private Integer internetId;
}
