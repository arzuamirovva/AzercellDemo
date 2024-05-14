package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.IsteSenTariffEntity;
import com.finalproject.azercell.entity.NumberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IsteSenTariffRepository extends CrudRepository<IsteSenTariffEntity, Integer> {


    void deleteByNumber(NumberEntity number);
    Optional findByNumber(NumberEntity number);
}
