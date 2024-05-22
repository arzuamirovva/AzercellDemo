package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.YourOwnTariffEntity;
import com.finalproject.azercell.entity.TariffEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.exception.NumberIsNotActiveException;
import com.finalproject.azercell.exception.TariffCreateException;
import com.finalproject.azercell.mapper.NumberMapper;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.repository.BalanceHistoryRepository;
import com.finalproject.azercell.repository.IsteSenTariffRepository;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.TariffRepository;

import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;



    public List<NumberDto> getAll(){
        log.info("ActionLog.NumberService.getAll has started");

        List<NumberDto> list = numberRepository.findAll().stream().map(numberMapper::mapToDto).toList();
        log.info("ActionLog.NumberService.getAll has ended");

        return list;
    }

    public NumberDto get(Integer id){
        log.info("ActionLog.NumberService.get has started for number {}", id);

        NumberDto dto = numberMapper.mapToDto(numberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("NUMBER_NOT_FOUND"))
        );
        log.info("ActionLog.NumberService.get has ended for number {}", id);
        return dto;
    }

    public void deactivateNumber(Integer id){
        log.info("ActionLog.NumberService.deactivateNumber has started for number {}", id);

        NumberEntity numberEntity = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found"));

        numberEntity.setStatus(NumberStatus.MANUAL_DEACTIVATED);
        numberRepository.save(numberEntity);
        log.info("ActionLog.NumberService.deactivateNumber has ended for number {}", id);
    }

    public void deactivateOwnNumber(HttpServletRequest request){
        Integer id = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.NumberService.deactivateNumber has started for number {}", id);

        NumberEntity numberEntity = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found"));

        numberEntity.setStatus(NumberStatus.MANUAL_DEACTIVATED);
        numberRepository.save(numberEntity);
        log.info("ActionLog.NumberService.deactivateNumber has ended for number {}", id);
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
        log.info("ActionLog.NumberService.calculateNewStatus has started for number {}", numberEntity.getId());

        LocalDateTime lastTransactionDate = balanceHistoryRepository.findLastTransactionDateByNumber(numberEntity)
                .orElseThrow(() -> new NotFoundException("Last transaction date not found"));

        if (lastTransactionDate.isBefore(LocalDateTime.now().minusMonths(1))) {
            log.info("ActionLog.NumberService.calculateNewStatus has ended with status ONE_SIDED_DEACTIVATED for number {}", numberEntity.getId());
            return NumberStatus.ONE_SIDED_DEACTIVATED;
        } else {
            log.info("ActionLog.NumberService.calculateNewStatus has ended with status TWO_SIDED_DEACTIVATED for number {}", numberEntity.getId());
            return NumberStatus.TWO_SIDED_DEACTIVATED;
        }
    }

    public void assignTariffToNumber(HttpServletRequest request, Integer tariffId) {
        log.info("ActionLog.NumberService.assignTariffToNumber has started");
        Integer id = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));
        if (number.getStatus() != NumberStatus.ACTIVE){
            throw new NumberIsNotActiveException("Number is not active");
        }
        TariffEntity tariffEntity = tariffRepository.findById(tariffId).orElseThrow(() -> new NotFoundException("This tariff is not found"));
        number.setTariff(tariffEntity);
        number.setInternetBalance(tariffEntity.getInternetAmount());
        number.setMinuteBalance(tariffEntity.getMinuteAmount());
        number.setSmsBalance(tariffEntity.getSmsAmount());
        number.setAssignTime(LocalDateTime.now());

        double totalCharge = tariffEntity.getSubscriptionPrice()+tariffEntity.getMonthlyPrice();
        if (number.getBalance() < totalCharge){
            throw new NotEnoughBalanceException("Not enough balance");
        }
        number.setBalance(number.getBalance() - totalCharge);
        numberRepository.save(number);


        log.info("ActionLog.NumberService.assignTariffToNumber has ended");
    }

    public void removeSubscriptionForNumber(Integer id) {

        log.info("ActionLog.NumberService.removeSubscriptionForNumber has started for number {}", id);

        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));
        number.setTariff(null);
        number.setInternetBalance((double) 0);
        number.setMinuteBalance(0);
        number.setSmsBalance(0);

        numberRepository.save(number);
        log.info("ActionLog.NumberService.removeSubscriptionForNumber has ended for number {}", id);

    }

    public Double checkPriceForYourOwn(Integer minutes, Double internetGb){
        log.info("ActionLog.NumberService.checkPriceForYourOwn has started");
        if (internetGb < 3 && minutes < 30) {
            throw new TariffCreateException("InternetGb must be more than 3GB and Minutes must be more than 30 minutes");
        }
        else {
            Double totalCharge = 7 + minutes * YourOwnTariffEntity.chargePerMinute + internetGb * YourOwnTariffEntity.chargePerGB;
            log.info("ActionLog.NumberService.checkPriceForYourOwn has ended");
            return totalCharge;
        }
    }

    public void connectToOwnTariff(Integer minutes, Double internetGb, Integer id){
        log.info("ActionLog.NumberService.connectToOwnTariff has started for number {}", id);
        NumberEntity number = numberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Number not found"));
        if (number.getStatus() != NumberStatus.ACTIVE){
            throw new NumberIsNotActiveException("Number is not active");
        }
        number.setTariff(null);
        YourOwnTariffEntity newTariff = YourOwnTariffEntity.builder().number(number)
                .minutes(minutes)
                .internetAmount(internetGb)
                .totalCharge(checkPriceForYourOwn(minutes, internetGb))
                .build();

        if (number.getBalance() < newTariff.getTotalCharge()){
            throw new NotEnoughBalanceException("Not enough balance");
        }
        if (number.getYourOwnTariff() != null){
            isteSenTariffRepository.deleteById((number.getYourOwnTariff().getId()));
        }
        isteSenTariffRepository.save(newTariff);
        number.setYourOwnTariff(newTariff);
        number.setBalance(number.getBalance() - newTariff.getTotalCharge());
        number.setMinuteBalance(newTariff.getMinutes());
        number.setInternetBalance(newTariff.getInternetAmount());
        number.setSmsBalance(0);

        numberRepository.save(number);
        log.info("ActionLog.NumberService.connectToOwnTariff has started for number {}", id);
    }

    public void removeYourOwnSubscriptionForNumber(Integer id) {
        log.info("ActionLog.NumberService.removeYourOwnSubscriptionForNumber has started for number {}", id);
        NumberEntity number = numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found with id: " + id));
        if (number.getYourOwnTariff() != null){
            isteSenTariffRepository.deleteById((number.getYourOwnTariff().getId()));
        }
        number.setMinuteBalance(0);
        number.setInternetBalance((double) 0);
        numberRepository.save(number);
        log.info("ActionLog.NumberService.removeYourOwnSubscriptionForNumber has started for number {}", id);
    }

    public void checkValidity(NumberEntity numberEntity){
        log.info("ActionLog.NumberService.checkValidity has started");
        if (numberEntity.getTariff() != null){
            TariffEntity tariff = numberEntity.getTariff();
           LocalDateTime endDate = numberEntity.getAssignTime().plusHours(tariff.getValidPeriod());
           if (endDate.isBefore(LocalDateTime.now())){
               removeSubscriptionForNumber(numberEntity.getId());
           }
        } else if (numberEntity.getYourOwnTariff() != null) {
            YourOwnTariffEntity yourOwnTariff = numberEntity.getYourOwnTariff();
            LocalDateTime endDate = numberEntity.getAssignTime().plusHours(yourOwnTariff.getValidPeriod());
            if (endDate.isBefore(LocalDateTime.now())){
                removeYourOwnSubscriptionForNumber(numberEntity.getId());
            }
        }
        log.info("ActionLog.NumberService.checkValidity has ended");
    }
}
