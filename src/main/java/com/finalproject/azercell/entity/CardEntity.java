package com.finalproject.azercell.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Table(name = "cards")
@Entity
@Data
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String number;
    private String CVV;
    private LocalDate expDate;
    private Double balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void createBalance(){
        Random random = new Random();
        Double test = random.nextDouble(0,5000) + 1;
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        this.balance = Double.parseDouble(df.format(test));
    }

}
