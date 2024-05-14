package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PatchMapping("/calls/{numberId}/{durationInMinutes}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForCallDuration(@PathVariable int numberId, @PathVariable int durationInMinutes) {
        billingService.chargeForCallDuration(numberId,durationInMinutes);
    }

    @PatchMapping("/internet/{numberId}/{dataUsageInMB}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForInternetUsage(@PathVariable int numberId, @PathVariable double dataUsageInMB) {
        billingService.chargeForInternetUsage(numberId,dataUsageInMB);
    }

    @PatchMapping("/sms/{numberId}/{numberOfMessages}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForSentMessages(@PathVariable int numberId, @PathVariable int numberOfMessages) {
        billingService.chargeForSentMessages(numberId,numberOfMessages);
    }
}
