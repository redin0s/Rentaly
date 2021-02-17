package com.folders.rentaly.persistence.dao;

import java.util.List;

import com.folders.rentaly.model.Check;
import com.folders.rentaly.model.User;

public interface CheckDAO extends DAO<Check> {
	List<Check> findByRent_Realty_Owner(User owner);
}
