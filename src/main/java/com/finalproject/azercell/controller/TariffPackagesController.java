package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.TariffPackageEntity;
import com.finalproject.azercell.model.TariffPackagesDto;
import com.finalproject.azercell.service.TariffPackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tariff/packages")
@RequiredArgsConstructor
public class TariffPackagesController {

    private final TariffPackagesService tariffPackagesService;

    @PostMapping
    public void create(@RequestBody TariffPackagesDto tariffPackagesDto){
        tariffPackagesService.create(tariffPackagesDto);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody TariffPackagesDto tariffPackagesDto){
        tariffPackagesService.update(id, tariffPackagesDto);
    }

    @GetMapping("/{id}")
    public TariffPackagesDto get(@PathVariable Integer id){
        return tariffPackagesService.get(id);
    }

    @GetMapping
    public List<TariffPackagesDto> getAll(){
        return tariffPackagesService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        tariffPackagesService.delete(id);
    }
}
