package com.folders.rentaly.persistence;

import com.folders.rentaly.model.Insertion;

import org.springframework.data.repository.CrudRepository;

public interface InsertionRepository extends CrudRepository<Insertion, Integer> {
    
}
