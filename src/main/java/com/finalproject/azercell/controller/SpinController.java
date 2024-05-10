package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.PrizeEntity;
import com.finalproject.azercell.service.SpinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/spin")
@RequiredArgsConstructor
public class SpinController {

    private final SpinService spinService;

    @PostMapping
    public void spin(@RequestHeader Integer numberId) {
        spinService.spin(numberId);
    }
}
