package com.finalproject.azercell.controller;
import com.finalproject.azercell.model.TariffDto;
import com.finalproject.azercell.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tariff")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TariffDto tariffDto) {
        tariffService.create(tariffDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody TariffDto tariffDto) {
        tariffService.update(id, tariffDto);
    }

    @GetMapping("/{id}")
    public TariffDto get(@PathVariable Integer id) {
        return tariffService.get(id);
    }

    @GetMapping
    public List<TariffDto> getAll() {
        return tariffService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        tariffService.delete(id);
    }

}
