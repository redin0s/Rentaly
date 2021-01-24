package com.folders.rentaly.persistence;

import java.util.List;

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.Realty;

import org.springframework.data.repository.CrudRepository;

public interface InsertionRepository extends CrudRepository<Insertion, Integer> {
    // List<Insertion> findByOwner_Realty_Owner_Id(@Param("id") Integer owner_id);
    List<Insertion> findByRealtyIn(List<Realty> realty);
}
