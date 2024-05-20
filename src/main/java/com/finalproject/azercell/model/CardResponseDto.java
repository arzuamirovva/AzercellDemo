package com.finalproject.azercell.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private LocalDate expDate;
    private Double balance;
    private String ownerName;
}
