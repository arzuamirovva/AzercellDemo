package com.finalproject.azercell.entity;

import com.finalproject.azercell.enums.GenderType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "passport")
@Entity
@Data
public class PassportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String serialNumber;
    private String fin;

    @OneToOne(cascade = CascadeType.PERSIST,mappedBy = "passport")
    private UserEntity user;
}
