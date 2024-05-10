package com.finalproject.azercell.repository;

import com.finalproject.azercell.entity.InternetPackageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface InternetPackagesRepository extends CrudRepository<InternetPackageEntity,Integer>{
    List findAll();

}
