package com.folders.rentaly.persistence;

import java.util.List;
import java.util.Optional;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

import org.springframework.data.repository.CrudRepository;

public interface RealtyRepository extends CrudRepository<Realty, Integer> {
    List<Realty> findByOwner(User owner);
    
    Optional<Realty> findByIdAndOwner(Integer id, User owner);

    List<Realty> findByOwnerAndInsertionNull(User owner);
    List<Realty> findByOwnerAndInsertionNotNull(User owner);

    List<Realty> findByOwnerAndDraftFalse(User owner);
    List<Realty> findByOwnerAndDraftTrue(User owner);
}
