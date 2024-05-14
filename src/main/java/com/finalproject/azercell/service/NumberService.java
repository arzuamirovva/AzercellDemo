package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.IsteSenTariffEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.exception.TariffCreateException;
import com.finalproject.azercell.mapper.NumberMapper;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.repository.BalanceHistoryRepository;
import com.finalproject.azercell.repository.IsteSenTariffRepository;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.TariffRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberService {

    private final NumberMapper numberMapper;
    private final NumberRepository numberRepository;
    private final BalanceHistoryRepository balanceHistoryRepository;
    private final IsteSenTariffRepository isteSenTariffRepository;
    private final TariffRepository tariffRepository;



    public List<NumberDto> getAll(){
        log.info("ActionLog.NumberService.getAll has started");

        List<NumberDto> list = numberRepository.findAll().stream().map(e -> numberMapper.mapToDto((NumberEntity) e)).toList();
        log.info("ActionLog.NumberService.getAll has ended");

        return list;
    }

    public NumberDto get(Integer id){
        log.info("ActionLog.NumberService.get has started");

        NumberDto dto = numberMapper.mapToDto(numberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("NUMBER_NOT_FOUND"))
        );
        log.info("ActionLog.NumberService.get has ended");
        return dto;
    }

    public void deactivateNumber(Integer id){
        log.info("ActionLog.NumberService.deactivateNumber has started");

        NumberEntity numberEntity = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found"));

        numberEntity.setStatus(NumberStatus.MANUAL_DEACTIVATED);
        numberRepository.save(numberEntity);
        log.info("ActionLog.NumberService.deactivateNumber has ended");
    }


    public void deactivateNumbersDaily() {
        log.info("ActionLog.NumberService.deactivateNumbersDaily has started");

        List<NumberEntity> numbers = numberRepository.findAll();
        for (NumberEntity numberEntity : numbers) {
            NumberStatus newStatus = calculateNewStatus(numberEntity);
            if (newStatus != numberEntity.getStatus()) {
                numberEntity.setStatus(newStatus);
                numberRepository.save(numberEntity);
            }
        }
        log.info("ActionLog.NumberService.deactivateNumbersDaily has ended");
    }

    public NumberStatus calculateNewStatus(NumberEntity numberEntity) {
        LocalDateTime lastTransactionDate = balanceHistoryRepository.findLastTransactionDateByNumber(numberEntity)
                .orElseThrow(() -> new NotFoundException("Last transaction date not found"));

        if (lastTransactionDate.isBefore(LocalDateTime.now().minusMonths(1))) {
            return NumberStatus.ONE_SIDED_DEACTIVATED;
        } else {
            return NumberStatus.TWO_SIDED_DEACTIVATED;
        }
    }

    public void assignTariffToNumber(Integer id, TariffEntity tariffEntity) {
        log.info("ActionLog.NumberService.assignTariffToNumber has started");

        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));

        number.setTariff(tariffEntity);
        number.setInternetBalance(tariffEntity.getInternetAmount());
        number.setMinuteBalance(tariffEntity.getMinuteAmount());
        number.setSmsBalance(tariffEntity.getSmsAmount());

        double totalCharge = tariffEntity.getSubscriptionPrice()+tariffEntity.getMonthlyPrice();
        number.setBalance(number.getBalance() - totalCharge);
        numberRepository.save(number);

        log.info("ActionLog.NumberService.assignTariffToNumber has ended");
    }


    public void removeSubscriptionForNumber(Integer id) {
        log.info("ActionLog.NumberService.removeSubscriptionForNumber has started");



        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));
        number.setTariff(null);

        numberRepository.save(number);
        log.info("ActionLog.NumberService.removeSubscriptionForNumber has ended");

    }

    public Double checkPriceForIsteSen(Integer minutes, Double internetGb){
        log.info("ActionLog.NumberService.checkPriceForIsteSen has started");
        if (internetGb < 3 && minutes < 30) {
            throw new TariffCreateException("InternetGb must be more than 3GB and Minutes must be more than 30 minutes");
        }
        else {
            Double totalCharge = 7 + minutes * IsteSenTariffEntity.chargePerMinute + internetGb * IsteSenTariffEntity.chargePerGB;
            log.info("ActionLog.NumberService.checkPriceForIsteSen has ended");
            return totalCharge;
        }
    }

    public void connectToIsteSen(Integer minutes, Double internetGb, Integer id){
        log.info("ActionLog.NumberService.connectToOwnPackage has started");

        NumberEntity number = numberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Number not found"));

        IsteSenTariffEntity newTariff = IsteSenTariffEntity.builder().number(number)
                .minutes(minutes)
                .internetAmount(internetGb)
                .totalCharge(checkPriceForIsteSen(minutes, internetGb))
        .build();

        number.setTariff(null);
        isteSenTariffRepository.save(newTariff);
        number.setIsteSenTariff(newTariff);
        number.setBalance(number.getBalance() - newTariff.getTotalCharge());
        numberRepository.save(number);
        log.info("ActionLog.NumberService.connectToOwnPackage has ended");


    }

    public void removeIsteSenSubscriptionForNumber(Integer id) {
        log.info("ActionLog.NumberService.removeIsteSenSubscriptionForNumber has started");
        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));
        number.setIsteSenTariff(null);

        numberRepository.save(number);
        log.info("ActionLog.NumberService.removeIsteSenSubscriptionForNumber has ended");

    }
}
