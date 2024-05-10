package com.finalproject.azercell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Data
public class UserRequestDto {

    public UserRequestDto() {
        this.registerDate=LocalDate.now();
    }

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 3, max = 20, message = "Full name must be between 3 and 20 characters")
    private String fullName;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private String number;
    @JsonIgnore
    private LocalDate registerDate;

    @Size(min = 7, max = 7)
    private String fin;

}
