package com.folders.rentaly.DBThingy.dao;

import java.util.Optional;

import com.folders.rentaly.model.User;

public interface UserDAO {
	public Optional<User> findUser(String email);
	public boolean registerUser(String email, String password);
}