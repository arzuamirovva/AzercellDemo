package com.finalproject.azercell.model;

import com.finalproject.azercell.enums.PrizeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrizeDto {

    private Integer id;
    private String name;
    private PrizeType prizeType;
    private int amount;
    private String description;

}
