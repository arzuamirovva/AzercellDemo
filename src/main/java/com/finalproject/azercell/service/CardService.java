package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.CardEntity;
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
        Integer userId = cardRequestDto.getUserId();
        CardEntity cardEntity = cardMapper.mapToEntity(cardRequestDto);
        cardEntity.setUser(userRepository.findById(userId).get());
        cardEntity.createBalance();
        cardRepository.save(cardEntity);
    }

    public void update(Integer id, CardRequestDto cardRequestDto){
        if (!cardRepository.existsById(id)){
            throw new RuntimeException("THIS_CARD_IS_NOT_FOUND");
        }
        var entity = cardMapper.mapToEntity(cardRequestDto);
        entity.setId(id);
    }

    public List getAll(){
         return cardRepository.findAll().stream().map(e -> {
             CardResponseDto dtoElement = cardMapper.mapToDto((CardEntity) e);
             dtoElement.setOwnerName(((CardEntity) e).getUser().getFullName());
             return dtoElement;
         }).toList();
    }

    public CardResponseDto get(Integer id){
        CardResponseDto dto = cardMapper.mapToDto(cardRepository.findById(id).orElseThrow(() -> new RuntimeException("CARD_IS_NOT_FOUND")));
        String ownerName = userRepository.findById(cardRepository.findById(id).get().getUser().getId()).get().getFullName();

        dto.setOwnerName(ownerName);
        return dto;
    }

    public void delete(Integer id){
        if (!cardRepository.existsById(id)){
            throw new RuntimeException("THIS_CARD_IS_NOT_FOUND");
        }
        cardRepository.deleteById(id);
    }
}
