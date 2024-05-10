package com.finalproject.azercell.model;

import com.finalproject.azercell.entity.PaymentEntity;
import com.finalproject.azercell.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CardResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private LocalDate expDate;
    private Double balance;
    private String ownerName; //?
}
