package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Table( name = "tariffPackages")
@Entity
@Data
public class TariffPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer minuteLimit;
    private Integer smsLimit;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tariff")
    private List<NumberEntity> numbers;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tariff")
//    private PaymentEntity payment;
}
