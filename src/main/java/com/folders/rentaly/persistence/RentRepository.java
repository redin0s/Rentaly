package com.folders.rentaly.persistence;

import java.time.LocalDate;
import java.util.List;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<Rent, Integer> {
    List<Rent> findByRealty(Realty realty);
    List<Rent> findByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end);
    List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end);
}
