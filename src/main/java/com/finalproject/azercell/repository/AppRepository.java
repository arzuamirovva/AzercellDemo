package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.AppEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppRepository extends CrudRepository<AppEntity,Integer> {

    List<AppEntity> findAll();

}
