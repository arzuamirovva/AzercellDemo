package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Table( name = "apps")
@Entity
@Data
public class AppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;
    private LocalDate startDate;
}
