package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.YourOwnTariffEntity;
import com.finalproject.azercell.entity.NumberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IsteSenTariffRepository extends CrudRepository<YourOwnTariffEntity, Integer> {


    void deleteByNumber(NumberEntity number);
    Optional findByNumber(NumberEntity number);
}
