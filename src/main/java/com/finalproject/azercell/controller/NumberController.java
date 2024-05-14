package com.finalproject.azercell.controller;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.service.NumberService;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("/status/{id}")
    public void deactivateNumber(@PathVariable Integer id){
        numberService.deactivateNumber(id);
    }

    @PostMapping("/assign-tariff/{id}")
    public void assignTariffToNumber(@PathVariable Integer id, @RequestBody TariffEntity tariffEntity) {
        numberService.assignTariffToNumber(id,tariffEntity);
    }

    @PutMapping("/remove-tariff/{id}")
    public void removeSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeSubscriptionForNumber(id);
    }

    @GetMapping("/istesen/total-charge/")
    public Double checkPriceForIsteSen(@RequestParam Integer minutes,@RequestParam Double internetGb) {
        return numberService.checkPriceForIsteSen(minutes, internetGb);
    }

    @PutMapping("/istesen/connect/{id}")
    public void connectToIsteSen(@RequestParam Integer minutes,@RequestParam Double internetGb,@PathVariable Integer id) {
        numberService.connectToIsteSen(minutes, internetGb, id);
    }

    @PutMapping("/{id}")
    public void removeIsteSenSubscriptionForNumber(@PathVariable Integer id) {
        numberService.removeIsteSenSubscriptionForNumber(id);
    }
}
