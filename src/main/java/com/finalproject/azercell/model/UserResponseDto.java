package com.finalproject.azercell.model;

import com.finalproject.azercell.entity.NumberEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponseDto {
    private Integer id;
    private String fullName;
    private String email;
    private LocalDate registerDate;
    private List<NumberDto> numbers;
}
