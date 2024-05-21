package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table( name = "balanceHistory")
@Entity
@Data
public class BalanceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private LocalDateTime transactionDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "number_id")
    private NumberEntity number;

    private Integer userId;
}
