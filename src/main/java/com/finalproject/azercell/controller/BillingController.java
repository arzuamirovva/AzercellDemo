package com.finalproject.azercell.controller;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/calls/{numberId}/{durationInMinutes}")
    public void chargeForCallDuration(@PathVariable int numberId, @PathVariable int durationInMinutes) {
        billingService.chargeForCallDuration(numberId,durationInMinutes);
    }

    @PostMapping("/internet/{numberId}/{dataUsageInMB}")
    public void chargeForInternetUsage(@PathVariable int numberId, @PathVariable double dataUsageInMB) {
        billingService.chargeForInternetUsage(numberId,dataUsageInMB);
    }

    @PostMapping("/sms/{numberId}/{numberOfMessages}")
    public void chargeForSentMessages(@PathVariable int numberId, @PathVariable int numberOfMessages) {
        billingService.chargeForSentMessages(numberId,numberOfMessages);
    }
}
