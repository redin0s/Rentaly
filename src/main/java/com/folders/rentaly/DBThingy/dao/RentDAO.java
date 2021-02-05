package com.folders.rentaly.DBThingy.dao;

import java.time.LocalDate;
import java.util.List;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;

public interface RentDAO {
    List<Rent> findByRealty(Realty realty);
    List<Rent> findByRealty_OwnerAndEndGreaterThanEqual(User owner, LocalDate end);
    List<Rent> findByHolderAndEndGreaterThanEqual(User holder, LocalDate end);
}
