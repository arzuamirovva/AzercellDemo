package com.finalproject.azercell.controller;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.service.NumberService;
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
    @ResponseStatus(HttpStatus.OK)
    public List<NumberDto> getAll(){
        return numberService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NumberDto get(@PathVariable Integer id){
        return numberService.get(id);
    }

    @PutMapping("/assign-tariff/")
    @ResponseStatus(HttpStatus.OK)
    public void assignTariffToNumber(@RequestParam Integer id, @RequestParam Integer tariffId) {
        numberService.assignTariffToNumber(id,tariffId);
    }

    @GetMapping("/istesen/total-charge/")
    @ResponseStatus(HttpStatus.OK)
    public Double checkPriceForIsteSen(@RequestParam Integer minutes,@RequestParam Double internetGb) {
        return numberService.checkPriceForIsteSen(minutes, internetGb);
    }

    @PutMapping("/assign-istesen/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void connectToIsteSen(@RequestParam Integer minutes,@RequestParam Double internetGb,@PathVariable Integer id) {
        numberService.connectToIsteSen(minutes, internetGb, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIsteSenSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeIsteSenSubscriptionForNumber(id);
    }
    @PatchMapping("/remove-tariff/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeSubscriptionForNumber(id);
    }

    @PatchMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateNumber(@PathVariable Integer id){
        numberService.deactivateNumber(id);
    }
}
