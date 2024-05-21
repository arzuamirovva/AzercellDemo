package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.UserRequestDto;
import com.finalproject.azercell.model.UserResponseDto;
import com.finalproject.azercell.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/details")
    public UserResponseDto getDetails(HttpServletRequest request){
        return userService.getDetails(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwn(HttpServletRequest request) {
        userService.deleteOwn(request);
    }
}
