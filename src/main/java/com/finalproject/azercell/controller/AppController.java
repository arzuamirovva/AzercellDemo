package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.AppEntity;
import com.finalproject.azercell.model.AppDto;
import com.finalproject.azercell.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apps")
public class AppController {

    private final AppService appService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AppDto appDto){
        appService.create(appDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Integer id, @RequestBody AppDto appDto){
        appService.update(id, appDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AppEntity> getAll(){
        return appService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppEntity get(@PathVariable Integer id){
        return appService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        appService.delete(id);
    }
}
