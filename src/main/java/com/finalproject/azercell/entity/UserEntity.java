package com.finalproject.azercell.entity;

import com.finalproject.azercell.enums.RoleEnum;
import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Table(name = "users")
@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private LocalDate registerDate;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<CardEntity> cards;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "passport_id")
    private PassportEntity passport;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "user")
    private List<NumberEntity> numbers;
}
