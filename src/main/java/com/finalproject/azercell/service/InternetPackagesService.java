package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.InternetPackageEntity;
import com.finalproject.azercell.mapper.InternetPackagesMapper;
import com.finalproject.azercell.model.InternetPackagesDto;
import com.finalproject.azercell.repository.InternetPackagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InternetPackagesService {

    private final InternetPackagesRepository internetPackagesRepository;
    private final InternetPackagesMapper internetPackagesMapper;

    public void create(InternetPackagesDto internetPackagesDto){
//        internetPackagesDto.generateEndDate();
        var entity = internetPackagesMapper.mapToEntity(internetPackagesDto);

        internetPackagesRepository.save(entity);
    }

    public void update(Integer id, InternetPackagesDto internetPackagesDto){
        var entity = internetPackagesMapper.mapToEntity(internetPackagesDto);
        entity.setId(id);
        internetPackagesRepository.save(entity);
    }

    public InternetPackagesDto get(Integer id){
        return internetPackagesMapper.mapToDto(internetPackagesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("INTERNET_PACKAGE_IS_NOT_FOUND")
        ));
    }

    public List<InternetPackagesDto> getAll(){
        return internetPackagesRepository.findAll().stream().map(e -> internetPackagesMapper.mapToDto((InternetPackageEntity) e)).toList();
    }

    public void delete(Integer id){
        internetPackagesRepository.deleteById(id);
    }

}
