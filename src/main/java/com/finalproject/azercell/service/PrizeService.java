package com.finalproject.azercell.service;

import com.finalproject.azercell.exception.NotFoundException;
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
        log.info("ActionLog.PrizeService.get has started");

        PrizeDto dto = prizeMapper.mapToDto(prizeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("PRIZE_NOT_FOUND")
        ));
        log.info("ActionLog.PrizeService.get has ended");
        return dto;
    }

    public void create(PrizeDto prizeDto) {
        log.info("ActionLog.PrizeService.create has started");

        var entity = prizeMapper.mapToEntity(prizeDto);
        prizeRepository.save(entity);
        log.info("ActionLog.PrizeService.create has ended");

    }

    public void update(Integer id, PrizeDto prizeDto) {
        log.info("ActionLog.PrizeService.update has started");

        var entity = prizeMapper.mapToEntity(prizeDto);
        entity.setId(id);
        prizeRepository.save(entity);
        log.info("ActionLog.PrizeService.update has ended");

    }

    public void delete(Integer id) {
        log.info("ActionLog.PrizeService.delete has started");

        if (prizeRepository.existsById(id)){
            prizeRepository.deleteById(id);
            log.info("ActionLog.PrizeService.delete has started");

        }else{
            throw new NotFoundException("Prize not found");
        }
    }
}
