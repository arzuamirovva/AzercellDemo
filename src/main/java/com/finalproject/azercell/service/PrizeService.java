package com.finalproject.azercell.service;

import com.finalproject.azercell.mapper.PrizeMapper;
import com.finalproject.azercell.model.PrizeDto;
import com.finalproject.azercell.repository.PrizeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrizeService {
    private final PrizeRepository prizeRepository;
    private final PrizeMapper prizeMapper;

    public List<PrizeDto> getAll() {
        return prizeRepository.findAll().stream().map(prizeMapper::mapToDto).toList();
    }

    public PrizeDto get(Integer id) {
        return prizeMapper.mapToDto(prizeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("PRIZE_NOT_FOUND")
        ));
    }

    public void create(PrizeDto prizeDto) {
        var entity = prizeMapper.mapToEntity(prizeDto);
        prizeRepository.save(entity);
    }

    public void update(Integer id, PrizeDto prizeDto) {
        var entity = prizeMapper.mapToEntity(prizeDto);
        entity.setId(id);
        prizeRepository.save(entity);
    }

    public void delete(Integer id) {
        prizeRepository.deleteById(id);
    }
}
