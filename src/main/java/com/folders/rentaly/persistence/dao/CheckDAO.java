package com.folders.rentaly.persistence.dao;

import java.time.LocalDate;
import java.util.List;

import com.folders.rentaly.model.Check;
import com.folders.rentaly.model.User;

public interface CheckDAO extends DAO<Check> {
	List<Check> findByRent_Realty_OwnerAndPaid(User owner, Boolean isPaid);
	List<Check> findByRent_HolderAndPaid(User holder, Boolean isPaid);

	List<Check> findByOwnerAndExpireGreaterThanEqual(User owner, LocalDate date);
	List<Check> findByHolderAndExpireGreaterThanEqual(User holder, LocalDate date);

	Integer countByOwnerAndExpireGreaterThanEqual(User owner, LocalDate date);
	Integer countByHolderAndExpireGreaterThanEqual(User holder, LocalDate date);
}
