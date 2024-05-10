package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public void create(@RequestBody CardRequestDto cardRequestDto){
        cardService.create(cardRequestDto);
    }

    @PutMapping("/{id}")
    public void update (@PathVariable Integer id, @RequestBody CardRequestDto cardRequestDto){
        cardService.update(id, cardRequestDto);
    }

    @GetMapping("/{id}")
    public CardResponseDto get(@PathVariable Integer id){
        return cardService.get(id);
    }

    @GetMapping
    public List getAll(){
        return cardService.getAll();
    }
}
