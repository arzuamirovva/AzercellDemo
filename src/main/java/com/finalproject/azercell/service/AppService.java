package com.finalproject.azercell.service;


import com.finalproject.azercell.entity.AppEntity;
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

    public void create(AppDto appDto){
        var entity = appMapper.mapToEntity(appDto);
        appRepository.save(entity);
    }

    public void update(Integer id, AppDto appDto){
        var entity = appMapper.mapToEntity(appDto);
        entity.setId(id);
        appRepository.findById(id);
    }

    public List<AppEntity> getAll(){
        return appRepository.findAll().stream().map(e -> appMapper.mapToDto((AppEntity) e)).toList();
    }

    public AppEntity get(Integer id){
        return appRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("APP_IS_NOT_FOUND")
        );
    }

    public void delete(Integer id){
        if (!appRepository.existsById(id)) {
            throw new RuntimeException("THIS_TARIFF_PACKAGE_IS_NOT_FOUND");
        }
        appRepository.deleteById(id);
    }
}
