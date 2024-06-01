package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.BalanceHistoryEntity;
import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.exception.UnknownCardException;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.model.YourOwnDto;
import com.finalproject.azercell.service.CardService;
import com.finalproject.azercell.service.NumberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/numbers")
@RequiredArgsConstructor
public class NumberController {

    private final NumberService numberService;

    @GetMapping
    public List<NumberDto> getAll(){
        return numberService.getAll();
    }

    @GetMapping("/{id}")
    public NumberDto get(@PathVariable Integer id){
        return numberService.get(id);
    }

    @PutMapping("/assign-tariff/")
    public void assignTariffToNumber(HttpServletRequest request, @RequestParam Integer tariffId) {
        numberService.assignTariffToNumber(request,tariffId);
    }

    @GetMapping("/your-own/total-charge/")
    public Double checkPriceForYourOwn(@RequestParam Integer minutes,@RequestParam Double internetGb) {
        return numberService.checkPriceForYourOwn(minutes, internetGb);
    }

    @PutMapping("/assign/your-own")
    public void connectToOwnTariff(@RequestParam Integer minutes, @RequestParam Double internetGb,HttpServletRequest request) {
         numberService.connectToOwnTariff(minutes, internetGb, request);
    }


    @PutMapping("/balance")
    public void increaseBalance(HttpServletRequest request, Double amount, Integer cardId) {
        numberService.increaseBalance(request, amount, cardId);
    }

    @PutMapping("/credit")
    public void addCreditToBalance(HttpServletRequest request) {
        numberService.addCreditToBalance(request);
    }

    @DeleteMapping("/remove/your-own/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeYourOwnSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeYourOwnSubscriptionForNumber(id);
    }
    @PutMapping("/remove-tariff/{id}")
    public void removeSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeSubscriptionForNumber(id);
    }

    @PutMapping("/status/{id}")
    public void deactivateNumber(@PathVariable Integer id){
        numberService.deactivateNumber(id);
    }
    @PutMapping("/own/status")
    public void deactivateOwnNumber(HttpServletRequest request){
        numberService.deactivateOwnNumber(request);
    }
}
