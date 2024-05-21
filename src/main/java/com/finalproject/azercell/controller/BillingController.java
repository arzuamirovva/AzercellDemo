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

    @PatchMapping("/calls/{durationInMinutes}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForCallDuration(HttpServletRequest request, @PathVariable int durationInMinutes) {
        billingService.chargeForCallDuration(request,durationInMinutes);
    }

    @PatchMapping("/internet/{dataUsageInMB}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForInternetUsage(HttpServletRequest request, @PathVariable double dataUsageInMB) {
        billingService.chargeForInternetUsage(request,dataUsageInMB);
    }

    @PatchMapping("/sms/{numberOfMessages}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chargeForSentMessages(HttpServletRequest request, @PathVariable int numberOfMessages) {
        billingService.chargeForSentMessages(request,numberOfMessages);
    }
}
