package com.finalproject.azercell.entity;

import com.finalproject.azercell.enums.NumberStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Table( name = "numbers")
@Entity
@Data
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    @Enumerated(EnumType.STRING)
    private NumberStatus status;
    private Double balance;
    private Boolean hasChance;
    private LocalDateTime lastSpinTime;
    private Integer freeMinutes=0;
    private Integer freeInternet=0;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_id")
    private TariffPackageEntity tariff;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "internet_id")
    private InternetPackageEntity internet;



//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "balance")
//    private BalanceEntity balance;
}