package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.model.UserResponseDto;
import com.finalproject.azercell.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody UserRequestDto userRequestDto) {
        userService.update(id, userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable Integer id){
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
