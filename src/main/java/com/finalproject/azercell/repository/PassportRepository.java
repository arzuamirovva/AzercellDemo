package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PassportRepository extends CrudRepository<PassportEntity, Integer> {

    Optional findByFin(String fin);
}
