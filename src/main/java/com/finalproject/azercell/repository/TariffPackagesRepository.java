package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.TariffPackageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffPackagesRepository extends CrudRepository<TariffPackageEntity, Integer>{

    List findAll();
}
