package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "istesen_tariff")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsteSenTariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_number")
    private NumberEntity number;

    private Integer minutes;
    private Double internetAmount;
    private Double totalCharge;

    public static final Double chargePerMinute = 0.02;
    public static final Double chargePerGB = 0.2;

}
