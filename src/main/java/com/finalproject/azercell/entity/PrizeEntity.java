package com.finalproject.azercell.entity;

import com.finalproject.azercell.enums.PrizeType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "prizes")
public class PrizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PrizeType prizeType;
    private int amount;
}
