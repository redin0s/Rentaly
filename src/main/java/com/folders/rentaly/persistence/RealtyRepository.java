package com.folders.rentaly.persistence;

import java.util.List;

import com.folders.rentaly.model.Realty;

import org.springframework.data.repository.CrudRepository;

public interface RealtyRepository extends CrudRepository<Realty, Integer> {
    List<Realty> findByOwnerId(Integer owner_id);
    
}
