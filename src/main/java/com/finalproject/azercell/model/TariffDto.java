package com.finalproject.azercell.model;

import com.finalproject.azercell.entity.NumberEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDto {

    private Integer id;
    private String name;
    private Double chargePerMinute;
    private Double chargePerMB;
    private Double chargePerSMS;
    private Integer minuteAmount;
    private Double internetAmount;
    private Integer smsAmount;
    private Double subscriptionPrice;
    private Double monthlyPrice;
    private String description;

//    public void generateEndDate() {
//        this.endDate = this.startDate.plusMonths(1);
//    }
}
