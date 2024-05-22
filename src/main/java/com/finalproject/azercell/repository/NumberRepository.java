package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NumberRepository extends CrudRepository<NumberEntity, Integer> {

    Optional<NumberEntity> findByNumber(String number);
    Optional<NumberEntity> findByUser(UserEntity user);

    List<NumberEntity> findAll();
    List<NumberEntity> findAllByUser(UserEntity userEntity);

}
