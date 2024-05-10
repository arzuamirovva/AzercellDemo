package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.AppEntity;
import com.finalproject.azercell.model.AppDto;
import com.finalproject.azercell.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apps")
public class AppController {

    private final AppService appService;

    @PostMapping
    public void create(@RequestBody AppDto appDto){
        appService.create(appDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody AppDto appDto){
        appService.update(id, appDto);
    }

    @GetMapping
    public List<AppEntity> getAll(){
        return appService.getAll();
    }

    @GetMapping("/{id}")
    public AppEntity get(@PathVariable Integer id){
        return appService.get(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        appService.delete(id);
    }
}
