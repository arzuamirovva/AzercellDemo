package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.IsteSenTariffEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsteSenTariffRepository extends CrudRepository<IsteSenTariffEntity, Integer> {
}
