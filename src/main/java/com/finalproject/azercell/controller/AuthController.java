package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.model.LoginRequestDto;
import com.finalproject.azercell.model.LoginResponseDto;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.repository.UserRepository;
import com.finalproject.azercell.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody UserRequestDto dto) {
        authService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginReq) {
        return authService.login(loginReq);
    }
}
