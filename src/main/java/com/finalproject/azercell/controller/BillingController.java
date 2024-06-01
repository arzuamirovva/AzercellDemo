package com.finalproject.azercell.controller;

import com.finalproject.azercell.service.BillingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PutMapping("/calls/{minutesUsed}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForCallDuration(HttpServletRequest request, @PathVariable int minutesUsed) {
        billingService.chargeForCallDuration(request,minutesUsed);
    }

    @PutMapping("/internet/{dataUsageInMB}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForInternetUsage(HttpServletRequest request, @PathVariable double dataUsageInMB) {
        billingService.chargeForInternetUsage(request,dataUsageInMB);
    }

    @PutMapping("/sms/{numberOfMessages}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForSentMessages(HttpServletRequest request, @PathVariable int numberOfMessages) {
        billingService.chargeForSentMessages(request,numberOfMessages);
    }
}
