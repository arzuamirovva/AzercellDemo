package com.finalproject.azercell.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDto {

    private Integer id;
    private String name;
    private Double price;
    private LocalDate startDate;
}
