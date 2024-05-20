package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.CardMapper;
import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.repository.CardRepository;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final NumberRepository numberRepository;
    private final BalanceHistoryService balanceHistoryService;

    public void create(CardRequestDto cardRequestDto){
        log.info("ActionLog.CardService.create has started for card with number: {}", cardRequestDto.getNumber());

        Integer userId = cardRequestDto.getUserId();
        CardEntity cardEntity = cardMapper.mapToEntity(cardRequestDto);
        cardEntity.setUser(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found")));
        cardEntity.createBalance();
        cardRepository.save(cardEntity);
        log.info("ActionLog.CardService.create has started for card with number: {}", cardRequestDto.getNumber());
    }

    public void update(Integer id, CardRequestDto cardRequestDto){
        log.info("ActionLog.CardService.update has started for card with id: {}", id);

        if (!cardRepository.existsById(id)){
            throw new NotFoundException("THIS_CARD_IS_NOT_FOUND");
        }
        var entity = cardMapper.mapToEntity(cardRequestDto);
        entity.setId(id);
        cardRepository.save(entity);
        log.info("ActionLog.CardService.update has ended for card with id: {}", id);
    }

    public List<CardResponseDto> getAll() {
        log.info("ActionLog.CardService.getAll has started");

        List<CardEntity> cardEntities = cardRepository.findAll();
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();

        for (CardEntity cardEntity : cardEntities) {
            CardResponseDto dtoElement = cardMapper.mapToDto(cardEntity);
            dtoElement.setOwnerName(cardEntity.getUser().getFullName());
            cardResponseDtos.add(dtoElement);
        }

        log.info("ActionLog.CardService.getAll has ended");
        return cardResponseDtos;
    }

    public CardResponseDto get(Integer id){
        log.info("ActionLog.CardService.get has started for id {}", id);

        CardResponseDto dto = cardMapper.mapToDto(cardRepository.findById(id).orElseThrow(() -> new NotFoundException("CARD_IS_NOT_FOUND")));
        String ownerName = userRepository.findById(cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User")).getUser().getId()).
                orElseThrow(() -> new NotFoundException("Not found a card")).getFullName();
        dto.setOwnerName(ownerName);
        log.info("ActionLog.CardService.get has ended for id {}", id);

        return dto;
    }

    public void delete(Integer id){
        log.info("ActionLog.CardService.delete has started for id {}", id);

        if (!cardRepository.existsById(id)){
            throw new NotFoundException("THIS_CARD_IS_NOT_FOUND");
        }
        cardRepository.deleteById(id);
        log.info("ActionLog.CardService.delete has ended for id {}", id);

    }

    public void increaseBalance(Integer numberId, Double amount, Integer cardId){
        log.info("ActionLog.CardService.increaseBalance has started for numberId: {} with amount: {} using cardId: {}", numberId, amount, cardId);
        CardEntity card = cardRepository.findById(cardId).orElseThrow(()-> new NotFoundException("Card is not found"));
        NumberEntity number = numberRepository.findById(numberId).orElseThrow(()-> new NotFoundException("Number is not found"));
        if (amount > card.getBalance()){
            throw new NotEnoughBalanceException("Not enough balance");
        }
        card.setBalance(card.getBalance() - amount);
        number.setBalance(number.getBalance() + amount);
        balanceHistoryService.addBalanceHistory(numberId, amount);
        cardRepository.save(card);
        numberRepository.save(number);
        log.info("ActionLog.CardService.increaseBalance has started for numberId: {} with amount: {} using cardId: {}", numberId, amount, cardId);
    }
}
