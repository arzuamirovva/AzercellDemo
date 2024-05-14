package com.finalproject.azercell.controller;
import com.finalproject.azercell.model.TariffDto;
import com.finalproject.azercell.service.TariffService;
import com.finalproject.azercell.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tariff")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;
//    private final NumberService numberService;

    @PostMapping
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
    public void delete(@PathVariable Integer id) {
        tariffService.delete(id);
    }

}
