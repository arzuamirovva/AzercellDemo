package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.NumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NumberRepository extends CrudRepository<NumberEntity, Integer> {

    Optional<NumberEntity> findByNumber(String number);
    List<NumberEntity> findAll();
}
