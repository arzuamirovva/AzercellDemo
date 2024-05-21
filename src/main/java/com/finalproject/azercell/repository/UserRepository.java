package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.PassportEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.model.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {

    List<UserEntity> findAll();

    Optional<UserEntity> findByPassport(PassportEntity passport);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.id = :id")
    void deleteById(@Param("id") Integer id);
}
