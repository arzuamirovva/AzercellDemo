package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.service.CardService;
import jakarta.servlet.http.HttpServletRequest;
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
    public void add(@RequestBody CardRequestDto cardRequestDto, HttpServletRequest request){
        cardService.addCard(cardRequestDto, request);
    }
//
//    @PutMapping("/{id}")
//    public void update (@PathVariable Integer id, @RequestBody CardRequestDto cardRequestDto){
//        cardService.update(id, cardRequestDto);
//    }

    @GetMapping("/{id}")
    public CardResponseDto get(@PathVariable Integer id){
        return cardService.get(id);
    }

    @GetMapping
    public List<CardResponseDto> getAll(){
        return cardService.getAll();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Integer numberId, @PathVariable Integer id) {
        cardService.delete(numberId, id);
    }

    @PatchMapping("/balance")
    public void increaseBalance(HttpServletRequest request, @RequestParam Double amount, @RequestParam Integer cardId){
        cardService.increaseBalance(request, amount, cardId);
    }
}
