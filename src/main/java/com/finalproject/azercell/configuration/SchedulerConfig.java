package com.finalproject.azercell.configuration;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.service.NumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    private final NumberService numberService;
    private final NumberRepository numberRepository;

    @Scheduled(fixedRate = 5000)
    public void checkNumberAndDeactivate(){
        numberRepository.findAll().forEach(number -> numberService.deactivateNumber((NumberEntity) number));
    }
}
