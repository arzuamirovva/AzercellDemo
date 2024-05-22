package com.finalproject.azercell.controller;

import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.service.NumberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/assign-your-own/{id}")
    public void connectToOwnTariff(@RequestParam Integer minutes,@RequestParam Double internetGb,@PathVariable Integer id) {
        numberService.connectToOwnTariff(minutes, internetGb, id);
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

    @PatchMapping("/status/{id}")
    public void deactivateNumber(@PathVariable Integer id){
        numberService.deactivateNumber(id);
    }
    @PatchMapping("/own/status")
    public void deactivateOwnNumber(HttpServletRequest request){
        numberService.deactivateOwnNumber(request);
    }
}
