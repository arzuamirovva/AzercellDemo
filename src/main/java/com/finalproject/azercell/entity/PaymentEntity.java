package com.finalproject.azercell.entity;

import com.finalproject.azercell.enums.PaymentMethod;
import com.finalproject.azercell.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "payments")
@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDate date;
    private Double amount;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private CardEntity card;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "internet" )
//    private InternetPackageEntity internet;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tariff")
//    private TariffPackageEntity tariff;
}
