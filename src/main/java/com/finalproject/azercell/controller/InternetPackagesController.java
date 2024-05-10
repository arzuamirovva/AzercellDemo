package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.InternetPackageEntity;
import com.finalproject.azercell.model.InternetPackagesDto;
import com.finalproject.azercell.service.InternetPackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internet/packages")
@RequiredArgsConstructor
public class InternetPackagesController {

    private final InternetPackagesService internetPackagesService;

    @PostMapping
    public void create(@RequestBody InternetPackagesDto internetPackagesDto) {
        internetPackagesService.create(internetPackagesDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody InternetPackagesDto internetPackagesDto) {
        internetPackagesService.update(id, internetPackagesDto);
    }

    @GetMapping("/{id}")
    public InternetPackagesDto get(@PathVariable Integer id) {
        return internetPackagesService.get(id);
    }

    @GetMapping
    public List<InternetPackagesDto> getAll() {
        return internetPackagesService.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        internetPackagesService.delete(id);
    }
}
