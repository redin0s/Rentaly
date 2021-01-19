package com.folders.rentaly.persistence;

import com.folders.rentaly.model.Rent;

import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent, Integer> {
    
}
