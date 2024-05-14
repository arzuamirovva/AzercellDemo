package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.TariffEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TariffRepository extends CrudRepository<TariffEntity,Integer>{
    List findAll();

}
