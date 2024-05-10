package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Table( name = "internetPackages")
@Entity
@Data
public class InternetPackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double speed;
    private Double dataLimit;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "internet")
    private List<NumberEntity> numbers;

}
