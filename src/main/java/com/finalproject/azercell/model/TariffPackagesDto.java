package com.finalproject.azercell.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffPackagesDto {

    private Integer id;
    private String name;
    private Integer minuteLimit;
    private Integer smsLimit;
    private Double price;
    private LocalDate startDate;
    private String description;
}
