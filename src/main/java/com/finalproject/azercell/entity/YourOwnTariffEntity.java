package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "your_own_tariff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YourOwnTariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer minutes;
    private Double internetAmount;
    private Double totalCharge;

    private final Integer validPeriod = 720;
    public static final Double chargePerMinute = 0.02;
    public static final Double chargePerGB = 0.2;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_number")
    private NumberEntity number;

//    @ManyToOne
//    @JoinColumn(name = "number_id")
//    private NumberEntity number;
}
