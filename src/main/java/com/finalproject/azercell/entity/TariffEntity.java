package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table( name = "tariffs")
@Entity
@Data
public class TariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Integer validPeriod;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "tariff")
    private List<NumberEntity> numbers;

}
