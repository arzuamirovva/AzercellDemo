package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.PrizeEntity;
import com.finalproject.azercell.model.PrizeDto;
import com.finalproject.azercell.service.SpinService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/spin")
@RequiredArgsConstructor
public class SpinController {

    private final SpinService spinService;

    @PutMapping
    public PrizeDto spin(HttpServletRequest request) {
        return spinService.spin(request);
    }
}
