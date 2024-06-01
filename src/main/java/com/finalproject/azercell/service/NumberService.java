package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.*;
import com.finalproject.azercell.enums.NumberStatus;
import com.finalproject.azercell.exception.*;
import com.finalproject.azercell.mapper.NumberMapper;
import com.finalproject.azercell.mapper.YourOwnMapper;
import com.finalproject.azercell.model.NumberDto;
import com.finalproject.azercell.model.YourOwnDto;
import com.finalproject.azercell.repository.*;

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
    private final CardRepository cardRepository;
    private final YourOwnMapper yourOwnMapper;



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
        log.info("ActionLog.NumberService.get has ended for number {}", id); //DTO
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

        assign(number, tariffEntity);

        double totalCharge = tariffEntity.getSubscriptionPrice()+tariffEntity.getMonthlyPrice();
        if (number.getBalance() < totalCharge){
            throw new NotEnoughBalanceException("Not enough balance");
        }
        number.setBalance(number.getBalance() - totalCharge);
        numberRepository.save(number);


        log.info("ActionLog.NumberService.assignTariffToNumber has ended");
    }

    public void assign(NumberEntity number, TariffEntity tariffEntity){
        number.setTariff(tariffEntity);
        number.setInternetBalance(tariffEntity.getInternetAmount());
        number.setMinuteBalance(tariffEntity.getMinuteAmount());
        number.setSmsBalance(tariffEntity.getSmsAmount());
        number.setAssignTime(LocalDateTime.now());
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

    public void connectToOwnTariff(Integer minutes, Double internetGb,HttpServletRequest request){
        Integer id = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
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

        YourOwnDto yourOwnDto = yourOwnMapper.mapToDto(newTariff);
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

    public void increaseBalance(HttpServletRequest request, Double amount, Integer cardId) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.NumberService.increaseBalance has started for numberId: {} with amount: {} and cardId: {}", numberId, amount, cardId);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found"));

        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card is not found"));

        if (!isCardOwner(number.getUser().getId(), cardId)) {
            throw new UnknownCardException("Card is unknown");
        }

        if (amount > card.getBalance()) {
            throw new NotEnoughBalanceException("Not enough balance in card");
        }
        card.setBalance(card.getBalance() - amount);
        number.setBalance(number.getBalance() + amount);
        if (number.getHasCredit()) {
            if (number.getBalance() >= 4) {
                number.setBalance(number.getBalance() - 4);
                number.setHasCredit(false);
            }
        }

        cardRepository.save(card);
        numberRepository.save(number);

        BalanceHistoryEntity balanceHistory = new BalanceHistoryEntity();
        balanceHistory.setNumber(number);
        balanceHistory.setAmount(amount);
        balanceHistory.setTransactionDate(LocalDateTime.now());
        balanceHistoryRepository.save(balanceHistory);

        log.info("ActionLog.NumberService.increaseBalance has ended for numberId: {} with amount: {} and cardId: {}", numberId, amount, cardId);
    }

    public void addCreditToBalance(HttpServletRequest request) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.NumberService.addCreditToBalance has started for numberId: {} ", numberId);

        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number not found"));

        number.setBalance(number.getBalance() + 3);
        number.setHasCredit(true);
        numberRepository.save(number);

        log.info("ActionLog.NumberService.addCreditToBalance has ended for numberId: {}", numberId);
    }

    private boolean isCardOwner(Integer userId, Integer cardId) {
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card Not Found"));

        return card.getUser().getId().equals(userId);
    }
}
