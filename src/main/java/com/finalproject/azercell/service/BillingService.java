package com.finalproject.azercell.service;
import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.repository.NumberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class BillingService {

    private final NumberRepository numberRepository;
    private final JwtUtil jwtUtil;


    private void checkNumberIsActive(NumberEntity number) {
        if (number.getStatus() != NumberStatus.ACTIVE) {
            throw new NotFoundException("Number is not active");
        }
    }

    public void chargeForCallDuration(HttpServletRequest request, int durationInMinutes) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));

        log.info("ActionLog.BillingService.chargeForCallDuration has started for {} and durationMinute is {}", numberId, durationInMinutes);
        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));
        checkNumberIsActive(number);
        if (number.getFreeMinutes() != null && number.getFreeMinutes() > 0) {
            if (number.getFreeMinutes() >= durationInMinutes) {
                number.setFreeMinutes(number.getFreeMinutes() - durationInMinutes);
                durationInMinutes = 0;
            } else {
                durationInMinutes -= number.getFreeMinutes();
                number.setFreeMinutes(0);
            }
        }

        if (durationInMinutes > 0) {
            if (number.getMinuteBalance() != null && number.getMinuteBalance() > 0) {
                if (number.getMinuteBalance() >= durationInMinutes) {
                    number.setMinuteBalance(number.getMinuteBalance() - durationInMinutes);
                    durationInMinutes = 0;
                } else {
                    durationInMinutes -= number.getMinuteBalance();
                    number.setMinuteBalance(0);
                }
            }
        }

        if (durationInMinutes > 0) {
            double totalCharge;
            if (number.getTariff() != null) {
                totalCharge = durationInMinutes * number.getTariff().getChargePerMinute();
            } else {
                totalCharge = durationInMinutes * 0.06;
            }
            if (number.getBalance() - totalCharge >= 0) {
                number.setBalance(number.getBalance() - totalCharge);
            } else {
                throw new NotEnoughBalanceException("Not Enough Balance");
            }
        }

        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForCallDuration has ended for {} and balance is {}", numberId, number.getBalance());
    }

    public void chargeForInternetUsage(HttpServletRequest request, double dataUsageInMB) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.BillingService.chargeForInternetUsage has started for {} and dataUsageInMB is {}", numberId, dataUsageInMB);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));
        checkNumberIsActive(number);
        double remainingDataUsage = dataUsageInMB;

        if (number.getFreeInternet() != null && number.getFreeInternet() > 0) {
            if (number.getFreeInternet() >= remainingDataUsage) {
                number.setFreeInternet(number.getFreeInternet() - remainingDataUsage);
                remainingDataUsage = 0;
            } else {
                remainingDataUsage -= number.getFreeInternet();
                number.setFreeInternet(0.0);
            }
        }

        if (remainingDataUsage > 0) {
            if (number.getInternetBalance() != null && number.getInternetBalance() > 0) {
                if (number.getInternetBalance() * 1000 >= remainingDataUsage) {
                    number.setInternetBalance(number.getInternetBalance() - (remainingDataUsage / 1000));
                    remainingDataUsage = 0;
                } else {
                    remainingDataUsage -= number.getInternetBalance() * 1000;
                    number.setInternetBalance(0.0);
                }
            }
        }

        if (remainingDataUsage > 0) {
            double totalCharge;
            if (number.getTariff() != null) {
                totalCharge = remainingDataUsage * number.getTariff().getChargePerMB();
            } else {
                totalCharge = remainingDataUsage * 0.03;
            }
            if (number.getBalance() - totalCharge >= 0) {
                number.setBalance(number.getBalance() - totalCharge);
            } else {
                throw new NotEnoughBalanceException("Not Enough Balance");
            }
        }

        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForInternetUsage has ended for {} and internet balance is {}", numberId, number.getInternetBalance());
    }

    public void chargeForSentMessages(HttpServletRequest request, int numberOfMessages) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));

        log.info("ActionLog.BillingService.chargeForSentMessages has started for number {} and number of messages {}", numberId, numberOfMessages);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + numberId));
        checkNumberIsActive(number);
        int remainingMessages = numberOfMessages;

        if (number.getSmsBalance() != null && number.getSmsBalance() > 0) {
            if (number.getSmsBalance() >= remainingMessages) {
                number.setSmsBalance(number.getSmsBalance() - remainingMessages);
                remainingMessages = 0;
            } else {
                remainingMessages -= number.getSmsBalance();
                number.setSmsBalance(0);
            }
        }

        if (remainingMessages > 0) {
            double totalCharge;
            if (number.getTariff() != null) {
                totalCharge = remainingMessages * number.getTariff().getChargePerSMS();
            } else {
                totalCharge = remainingMessages * 0.22;
            }
            if (number.getBalance() - totalCharge >= 0) {
                number.setBalance(number.getBalance() - totalCharge);
            } else {
                throw new NotEnoughBalanceException("Not Enough Balance");
            }
        }

        numberRepository.save(number);
        log.info("ActionLog.BillingService.chargeForSentMessages has ended for number {} and message balance is {}", numberId, number.getSmsBalance());
    }
}
