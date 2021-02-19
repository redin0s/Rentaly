package com.folders.rentaly.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

public interface RealtyDAO extends DAO<Realty>{
    List<Realty> findByOwner(User owner);
    
    Optional<Realty> findByIdAndOwner(Integer id, User owner);

    List<Realty> findByOwnerAndInsertionNull(User owner);
    List<Realty> findByOwnerAndInsertionNotNull(User owner);

    List<Realty> findByOwnerAndDraft(User owner, Boolean draft);

    List<Realty> findClosestToPoint(Float lat, Float lon);

    Integer countByOwnerAndDraft(User owner, Boolean draft);
}
