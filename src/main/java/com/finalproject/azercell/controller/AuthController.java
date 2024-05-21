package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.LoginRequestDto;
import com.finalproject.azercell.model.LoginResponseDto;
import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRequestDto dto) {
        authService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginReq) {
        return authService.login(loginReq);
    }
}
