package com.finalproject.azercell.service;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class BillingService {

    private final NumberRepository numberRepository;


    public void chargeForCallDuration(int numberId, int durationInMinutes) {
        log.info("ActionLog.BillingService.chargeForCallDuration has started");

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));

        if (number.getMinuteBalance() == 0){
            double totalCharge = durationInMinutes * number.getTariff().getChargePerMinute();
            number.setBalance(number.getBalance()-totalCharge);
        }else {
            number.setMinuteBalance(number.getMinuteBalance() - durationInMinutes);
        }
        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForCallDuration has ended");

    }

    public void chargeForInternetUsage(int numberId, double dataUsageInMB) {
        log.info("ActionLog.BillingService.chargeForInternetUsage has started");

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));
        if (number.getInternetBalance() == null) {
            throw new NullPointerException("Internet balance is null for number with id: " + numberId);
        }
        if (number.getInternetBalance() == 0){
            double totalCharge = dataUsageInMB * number.getTariff().getChargePerMB();
            number.setBalance(number.getBalance() - totalCharge);
        } else {
            number.setInternetBalance(number.getInternetBalance()*1000 - dataUsageInMB);
        }
        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForInternetUsage has ended");
    }

    public void chargeForSentMessages(int numberId, int numberOfMessages) {
        log.info("ActionLog.BillingService.chargeForSentMessages has started");

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));

        if (number.getSmsBalance() == 0) {
            double totalCharge = numberOfMessages * number.getTariff().getChargePerSMS();
            number.setBalance(number.getBalance() - totalCharge);
        } else {
            number.setSmsBalance(number.getSmsBalance() - numberOfMessages);
        }
        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForSentMessages has ended");

    }
}
