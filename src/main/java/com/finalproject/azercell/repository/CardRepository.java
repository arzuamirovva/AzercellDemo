package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<CardEntity, Integer> {

    List findAll();
}
