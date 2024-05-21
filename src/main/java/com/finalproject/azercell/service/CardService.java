package com.finalproject.azercell.service;

import com.finalproject.azercell.configuration.security.JwtUtil;
import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.exception.NotEnoughBalanceException;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.exception.UnknownCardException;
import com.finalproject.azercell.mapper.CardMapper;
import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.repository.CardRepository;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    private final NumberRepository numberRepository;
    private final BalanceHistoryService balanceHistoryService;
    private final JwtUtil jwtUtil;


    public void addCard(CardRequestDto cardRequestDto, HttpServletRequest request){
        CardEntity card = cardRepository.findByNumber(cardRequestDto.getNumber())
                .orElseThrow(() -> new NotFoundException("Card not found in the database"));
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        CardResponseDto cardResponseDto = cardMapper.mapToDto(card);
        cardResponseDto.setOwnerName(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User Not Found")).getFullName());
        card.setUser(userEntity);
        cardRepository.save(card);
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

    public CardResponseDto get(Integer id) {
        log.info("ActionLog.CardService.get has started for id {}", id);

        CardResponseDto dto = cardMapper.mapToDto(cardRepository.findById(id).orElseThrow(() -> new NotFoundException("CARD_IS_NOT_FOUND")));

        log.info("ActionLog.CardService.get has ended for id {}", id);
        return dto;
    }

    public void delete(Integer numberId, Integer id) {
        log.info("ActionLog.CardService.delete has started for id {}", id);
        if (!cardRepository.existsById(id)) {
            throw new NotFoundException("THIS_CARD_IS_NOT_FOUND");
        }
        if (!checkCardsOwner(numberId, id)) {
            cardRepository.deleteById(id);
        } else {
            throw new UnknownCardException("You can't delete this card");
        }
        log.info("ActionLog.CardService.delete has ended for id {}", id);
    }

    public void increaseBalance(HttpServletRequest request, Double amount, Integer cardId) {
        Integer numberId = jwtUtil.getNumberId(jwtUtil.resolveClaims(request));
        log.info("ActionLog.CardService.increaseBalance has started for numberId: {} with amount: {} using cardId: {}", numberId, amount, cardId);

        CardEntity card = cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("Card is not found"));
        NumberEntity number = numberRepository.findById(numberId).orElseThrow(() -> new NotFoundException("Number is not found"));

        if (!checkCardsOwner(numberId, cardId)) {
            throw new UnknownCardException("Card is unknown");
        }
        if (amount > card.getBalance()) {
            throw new NotEnoughBalanceException("Not enough balance");
        }
        card.setBalance(card.getBalance() - amount);
        number.setBalance(number.getBalance() + amount);
        balanceHistoryService.addBalanceHistory(numberId, amount);
        cardRepository.save(card);
        numberRepository.save(number);

        log.info("ActionLog.CardService.increaseBalance has ended for numberId: {} with amount: {} using cardId: {}", numberId, amount, cardId);
    }

    public boolean checkCardsOwner(Integer numberId, Integer cardId) {
        log.info("ActionLog.CardService.checkCardsOwner has started for numberId: {} and cardId: {}", numberId, cardId);
        boolean has = false;
        NumberEntity number = numberRepository.findById(numberId)
                .orElseThrow(() -> new NotFoundException("Number Not Found"));
        UserEntity user = number.getUser();
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card Not Found"));
        if (card.getUser().equals(user)) {
            has = true;
        }
        log.info("ActionLog.CardService.checkCardsOwner has ended for numberId: {} and cardId: {}", numberId, cardId);
        return has;
    }
}
