package com.finalproject.azercell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.azercell.enums.NumberStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Table(name = "numbers")
@Entity
@Data
@NoArgsConstructor
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String password;
    @Enumerated(EnumType.STRING)
    private NumberStatus status;

    private Double balance;
    private Double internetBalance;
    private Integer minuteBalance;
    private Integer smsBalance;

    private LocalDateTime lastSpinTime;
    private Boolean hasChance;
    private Integer freeMinutes=0;
    private Double freeInternet=0.0;
    private Boolean hasCredit;

    private LocalDateTime assignTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

    @OneToOne(mappedBy = "number")
//    @JoinColumn(name = "yourOwn_id")
    public YourOwnTariffEntity yourOwnTariff;

    @OneToMany(mappedBy = "number" ,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BalanceHistoryEntity> balanceHistoryList;

    public NumberEntity(String number, String password) {
        this.number = number;
        this.password = password;
    }

    public Integer getFreeMinutes() {
        return this.freeMinutes != null ? this.freeMinutes : 0;
    }
    public Double getFreeInternet() {
        return this.freeInternet != null ? this.freeInternet : 0.0;
    }
}