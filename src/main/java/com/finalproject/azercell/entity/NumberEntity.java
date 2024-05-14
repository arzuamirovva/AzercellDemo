package com.finalproject.azercell.entity;

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
    private Integer freeInternet=0;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff; // eslinde 1 nomrenin 1?

    @OneToOne(mappedBy = "number")
    @JoinColumn(name = "isteSenTariff_id")
    public IsteSenTariffEntity isteSenTariff;

//    @OneToMany(mappedBy = "number")
//    private List<IsteSenTariffEntity> isteSenTariff;
    public NumberEntity(String number, String password) {
        this.number = number;
        this.password = password;
    }

}