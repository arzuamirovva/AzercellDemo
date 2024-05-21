package com.finalproject.azercell.service;

import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.TariffMapper;
import com.finalproject.azercell.model.TariffDto;
import com.finalproject.azercell.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffService {

    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    public void create(TariffDto tariffDto){
        log.info("ActionLog.TariffService.create has started for tariff {}", tariffDto.getName());

        var entity = tariffMapper.mapToEntity(tariffDto);

        tariffRepository.save(entity);
        log.info("ActionLog.TariffService.create has ended for tariff {}", tariffDto.getName());
    }

    public void update(Integer id, TariffDto tariffDto){
        log.info("ActionLog.TariffService.update has started for tariff {}", id);

        var entity = tariffMapper.mapToEntity(tariffDto);
        entity.setId(id);
        tariffRepository.save(entity);
        log.info("ActionLog.TariffService.update has ended for tariff {}", id);

    }

    public TariffDto get(Integer id){
        log.info("ActionLog.TariffService.get has started for tariff {}", id);

        TariffDto dto = tariffMapper.mapToDto(tariffRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Tariff_IS_NOT_FOUND")
        ));
        log.info("ActionLog.TariffService.get has ended for tariff {}", id);

        return dto;
    }

    public List<TariffDto> getAll() {
        log.info("ActionLog.TariffService.getAll has started");

        List<TariffDto> list = tariffRepository.findAll().stream()
                .map(tariffMapper::mapToDto)
                .toList();

        log.info("ActionLog.TariffService.getAll has ended");
        return list;
    }

    public void delete(Integer id){
        log.info("ActionLog.TariffService.delete has started for tariff {}", id);
        if (tariffRepository.existsById(id)) {
            tariffRepository.deleteById(id);
            log.info("ActionLog.TariffService.delete has ended for tariff {}", id);
        }else {
            throw new NotFoundException("Not Found Tariff");
        }
    }
}
