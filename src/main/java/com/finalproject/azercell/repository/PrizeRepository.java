package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.PrizeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PrizeRepository extends CrudRepository<PrizeEntity, Integer> {

    List<PrizeEntity> findAll(); //check

    Optional<PrizeEntity> findById(Integer id); //check
}
