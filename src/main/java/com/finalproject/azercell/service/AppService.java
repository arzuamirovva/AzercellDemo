package com.finalproject.azercell.service;


import com.finalproject.azercell.entity.AppEntity;
import com.finalproject.azercell.exception.NotFoundException;
import com.finalproject.azercell.mapper.AppMapper;
import com.finalproject.azercell.model.AppDto;
import com.finalproject.azercell.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppService {

    private final AppMapper appMapper;
    private final AppRepository appRepository;

    public void create(AppDto appDto) {
        log.info("ActionLog.AppService.create has started for {}", appDto.getName());

        var entity = appMapper.mapToEntity(appDto);
        appRepository.save(entity);
        log.info("ActionLog.AppService.create has ended for {}", appDto.getName());

    }

    public void update(Integer id, AppDto appDto) {
        log.info("ActionLog.AppService.update has started for id {}", id);
        var entity = appMapper.mapToEntity(appDto);
        entity.setId(id);
        appRepository.findById(id);
        log.info("ActionLog.AppService.update has ended for id {}", id);
    }

    public List<AppDto> getAll() {
        log.info("ActionLog.AppService.getAll has started");
        List<AppDto> list = appRepository.findAll().stream().map(appMapper::mapToDto).toList();
        log.info("ActionLog.AppService.getAll has ended");
        return list;
    }

    public AppEntity get(Integer id) {
        log.info("ActionLog.AppService.get has started for id {}", id);
        AppEntity app = appRepository.findById(id).orElseThrow(
                () -> new NotFoundException("APP NOT FOUND")
        );
        log.info("ActionLog.AppService.get has ended for id {}", id);
        return app;
    }

    public void delete(Integer id) {
        log.info("ActionLog.AppService.delete has started for id {}", id);
        if (!appRepository.existsById(id)) {
            throw new NotFoundException("APP NOT FOUND");
        }
        appRepository.deleteById(id);
        log.info("ActionLog.AppService.delete has ended for id {}", id);

    }
}
