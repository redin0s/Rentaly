package com.folders.rentaly.persistence;

import com.folders.rentaly.model.Check;

import org.springframework.data.repository.CrudRepository;

public interface CheckRepository extends CrudRepository<Check, Integer> {
    
}
