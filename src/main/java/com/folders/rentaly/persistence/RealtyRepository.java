package com.folders.rentaly.persistence;

import com.folders.rentaly.model.Realty;

import org.springframework.data.repository.CrudRepository;

public interface RealtyRepository extends CrudRepository<Realty, Integer> {
    
}
