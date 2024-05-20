package com.finalproject.azercell.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Number cannot be blank")
    private String number;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
