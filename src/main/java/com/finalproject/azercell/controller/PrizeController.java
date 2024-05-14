package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.PrizeDto;
import com.finalproject.azercell.service.PrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/prize")
@RequiredArgsConstructor

public class PrizeController {
    private final PrizeService prizeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PrizeDto> getAll() {
        return prizeService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PrizeDto get(@PathVariable Integer id) {
        return prizeService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PrizeDto prizeDto) {
        prizeService.create(prizeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Integer id, @RequestBody PrizeDto prizeDto) {
        prizeService.update(id,prizeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        prizeService.delete(id);
    }
}
