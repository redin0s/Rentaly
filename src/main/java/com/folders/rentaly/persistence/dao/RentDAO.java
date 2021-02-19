package com.folders.rentaly.persistence.dao;

import java.time.LocalDate;
import java.util.List;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

public interface RentDAO extends DAO<Rent> {
    List<Rent> findByRealty(Realty realty);
    List<Rent> findByRealty_Owner(User owner);
    List<Rent> findByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end);
    List<Rent> findByHolder(User holder);
    List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end);

    Integer countByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end);
}
