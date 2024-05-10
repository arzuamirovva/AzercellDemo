package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.PrizeEntity;
import com.finalproject.azercell.enums.PrizeType;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.PrizeRepository;
import com.finalproject.azercell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpinService {

    private final NumberRepository numberRepository;
    private final PrizeRepository prizeRepository;

    public void checkSpinTime(Integer numberId) {
        NumberEntity number = numberRepository.findById(numberId).get();
        LocalDateTime lastSpinTime = number.getLastSpinTime();
        if (lastSpinTime == null || lastSpinTime.plusWeeks(1).isBefore(LocalDateTime.now())) {
            number.setHasChance(true);
            numberRepository.save(number);
        }
    }

    public void spin(Integer numberId) {
        NumberEntity number = numberRepository.findById(numberId).get();
        checkSpinTime(numberId);

        if (number.getHasChance()) {
            List<PrizeEntity> prizes = prizeRepository.findAll();
            PrizeEntity randomPrize = prizes.get(new Random().nextInt(prizes.size()));

            if (randomPrize.getPrizeType().equals(PrizeType.MINUTES)) {
                number.setFreeMinutes(number.getFreeMinutes() + randomPrize.getAmount());
                System.out.println("Minutes added");
            } else if (randomPrize.getPrizeType().equals(PrizeType.INTERNET)) {
                number.setFreeInternet(number.getFreeInternet() + randomPrize.getAmount());
                System.out.println("Internet added");
            }
            number.setHasChance(false);
            number.setLastSpinTime(LocalDateTime.now());
            numberRepository.save(number);
        } else {
            throw new RuntimeException("NO_CHANCE");
        }
    }
}
