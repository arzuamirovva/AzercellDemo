package com.finalproject.azercell.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternetPackagesDto {

    private Integer id;
    private String name;
    private Double speed;
    private Double dataLimit;
    private Double price;
    private LocalDate startDate;
    private String description;

//    public void generateEndDate() {
//        this.endDate = this.startDate.plusMonths(1);
//    }
}
