package com.finalproject.azercell.model;

import com.finalproject.azercell.enums.NumberStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberDto {

    private Integer id;
    private String number;
    private NumberStatus status;
    private Double balance;
    private Integer userId;
    private Integer tariffId;
    private Integer internetId;
}
