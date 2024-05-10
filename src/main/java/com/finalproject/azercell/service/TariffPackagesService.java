package com.finalproject.azercell.service;

import com.finalproject.azercell.entity.TariffPackageEntity;
import com.finalproject.azercell.mapper.TariffPackagesMapper;
import com.finalproject.azercell.model.TariffPackagesDto;
import com.finalproject.azercell.repository.TariffPackagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffPackagesService {

    private final TariffPackagesMapper tariffPackagesMapper;
    private final TariffPackagesRepository tariffPackagesRepository;

    public void create(TariffPackagesDto tariffPackagesDto){
        var entity = tariffPackagesMapper.mapToEntity(tariffPackagesDto);
        tariffPackagesRepository.save(entity);
    }

    public void update(Integer id, TariffPackagesDto tariffPackagesDto){
        var entity = tariffPackagesMapper.mapToEntity(tariffPackagesDto);
        entity.setId(id);
        tariffPackagesRepository.save(entity);
    }

    public List<TariffPackagesDto> getAll(){
        return tariffPackagesRepository.findAll().stream().map(e -> tariffPackagesMapper.mapToDto((TariffPackageEntity) e)).toList();
    }

    public TariffPackagesDto get(Integer id){
        return tariffPackagesMapper.mapToDto(tariffPackagesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("THIS_TARIFF_PACKAGE_IS_NOT_FOUND")
        ));
    }
    public void delete(Integer id){
        if (!tariffPackagesRepository.existsById(id)) {
            throw new RuntimeException("THIS_TARIFF_PACKAGE_IS_NOT_FOUND");
        }
        tariffPackagesRepository.deleteById(id);
    }
}
