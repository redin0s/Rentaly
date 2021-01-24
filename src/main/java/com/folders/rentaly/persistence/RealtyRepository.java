package com.folders.rentaly.persistence;

import java.util.List;
import java.util.Optional;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;

import org.springframework.data.repository.CrudRepository;

public interface RealtyRepository extends CrudRepository<Realty, Integer> {
    List<Realty> findByOwner(User owner);
    // List<Realty> findByOwner_Id(Integer owner_id);
    Optional<Realty> findByIdAndOwner(Integer id, User owner);
}
