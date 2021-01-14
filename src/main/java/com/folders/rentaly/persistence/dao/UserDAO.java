package com.folders.rentaly.persistence.dao;

import java.util.List;

import com.folders.rentaly.model.User;

public interface UserDAO {
	public List<User> findAll();
	public User findUser(String username);
	public boolean registerUser(String username, String password);
	public int getNumOfUsers();
}