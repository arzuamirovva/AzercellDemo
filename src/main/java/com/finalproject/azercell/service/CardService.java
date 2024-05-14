package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.CardMapper;
import com.finalproject.azercell.model.CardRequestDto;
import com.finalproject.azercell.model.CardResponseDto;
import com.finalproject.azercell.repository.CardRepository;
import com.finalproject.azercell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;
    public void create(CardRequestDto cardRequestDto){
        log.info("ActionLog.CardService.create has started");

        Integer userId = cardRequestDto.getUserId();
        CardEntity cardEntity = cardMapper.mapToEntity(cardRequestDto);
        cardEntity.setUser(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found")));
        cardEntity.createBalance();
        cardRepository.save(cardEntity);
        log.info("ActionLog.CardService.create has ended");

    }

    public void update(Integer id, CardRequestDto cardRequestDto){
        log.info("ActionLog.CardService.update has started");

        if (!cardRepository.existsById(id)){
            throw new NotFoundException("THIS_CARD_IS_NOT_FOUND");
        }
        var entity = cardMapper.mapToEntity(cardRequestDto);
        entity.setId(id);
        cardRepository.save(entity);
        log.info("ActionLog.CardService.update has ended");


    }

    public List getAll(){
        log.info("ActionLog.CardService.getAll has started");

         List list = cardRepository.findAll().stream().map(e -> {
             CardResponseDto dtoElement = cardMapper.mapToDto((CardEntity) e);
             dtoElement.setOwnerName(((CardEntity) e).getUser().getFullName());
             return dtoElement;
         }).toList();
        log.info("ActionLog.CardService.getAll has ended");
        return list;
    }

    public CardResponseDto get(Integer id){
        log.info("ActionLog.CardService.get has started");

        CardResponseDto dto = cardMapper.mapToDto(cardRepository.findById(id).orElseThrow(() -> new NotFoundException("CARD_IS_NOT_FOUND")));
        String ownerName = userRepository.findById(cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found User")).getUser().getId()).
                orElseThrow(() -> new NotFoundException("Not found a card")).getFullName();
        dto.setOwnerName(ownerName);
        log.info("ActionLog.CardService.get has ended");

        return dto;
    }

    public void delete(Integer id){
        log.info("ActionLog.CardService.delete has started");

        if (!cardRepository.existsById(id)){
            throw new NotFoundException("THIS_CARD_IS_NOT_FOUND");
        }
        cardRepository.deleteById(id);
        log.info("ActionLog.CardService.delete has ended");

    }
}
