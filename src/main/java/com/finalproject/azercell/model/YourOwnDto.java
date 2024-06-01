package com.finalproject.azercell.model;

import com.finalproject.azercell.entity.NumberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YourOwnDto {
    private Integer minutes;
    private Double internetAmount;
    private Double totalCharge;
    private final Integer validPeriod = 720;
    private NumberEntity number;
}
