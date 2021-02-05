package com.folders.rentaly.DBThingy.dao;

import java.util.List;
import java.util.Optional;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

public interface RealtyDAO {
    List<Realty> findByOwner(User owner);
    
    Optional<Realty> findById(Integer id);
    Optional<Realty> findByIdAndOwner(Integer id, User owner);

    List<Realty> findByOwnerAndInsertionNull(User owner);
    List<Realty> findByOwnerAndInsertionNotNull(User owner);

    List<Realty> findByOwnerAndDraftFalse(User owner);
    List<Realty> findByOwnerAndDraftTrue(User owner);
}
