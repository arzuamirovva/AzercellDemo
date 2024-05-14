package com.finalproject.azercell.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class LoginRequestDto {
    private String number;
    private String password;
}
