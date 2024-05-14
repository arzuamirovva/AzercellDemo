package com.finalproject.azercell.configuration.schedulers;

import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final NumberService numberService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deactivateNumberDaily(){
        numberService.deactivateNumbersDaily();
    }
}
