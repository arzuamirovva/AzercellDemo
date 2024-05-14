package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.PassportEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.model.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {

    List findAll();

    Optional findByPassport(PassportEntity passport);



}
