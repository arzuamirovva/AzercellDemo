package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.CardEntity;
import com.finalproject.azercell.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<CardEntity, Integer> {

    List<CardEntity> findAll();
    List<CardEntity> findAllByUser(UserEntity userEntity);

    Optional<CardEntity> findByNumber(String number);
    Optional<CardEntity> findByUser(UserEntity userEntity);
}
