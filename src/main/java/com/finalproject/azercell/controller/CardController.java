package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CardRequestDto cardRequestDto){
        cardService.create(cardRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update (@PathVariable Integer id, @RequestBody CardRequestDto cardRequestDto){
        cardService.update(id, cardRequestDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponseDto get(@PathVariable Integer id){
        return cardService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List getAll(){
        return cardService.getAll();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        cardService.delete(id);
    }

    @PatchMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public void increaseBalance(@RequestParam Integer numberId, @RequestParam Double amount,@RequestParam Integer cardId){
        cardService.increaseBalance(numberId, amount, cardId);
    }
}
