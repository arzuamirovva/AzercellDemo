package com.finalproject.azercell.configuration.schedulers;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final NumberService numberService;
    private final NumberRepository numberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deactivateNumberDaily(){
        numberService.deactivateNumbersDaily();
    }

    @Scheduled(fixedRate = 50000)
    public void resetTariffs(){
        List<NumberEntity> numberEntityList = numberRepository.findAll();

        for (NumberEntity entity : numberEntityList){
//            System.out.println(entity.getNumber());
            numberService.checkValidity(entity);
        }
    }
}
