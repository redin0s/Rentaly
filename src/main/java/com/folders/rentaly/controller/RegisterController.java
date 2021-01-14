package com.folders.rentaly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.persistence.DBManager;
import com.folders.rentaly.persistence.dao.UserDAO;

@Controller
public class RegisterController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("doRegister")
	public String register(@RequestParam String user, @RequestParam String pass) {
		System.out.println(user);
		System.out.println(pass);				
		
		UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();
		
		//TODO how to manage errors
		
		if (userDAO.findUser(user) != null) {
			return "error-existinguser";
		}
		else if (userDAO.registerUser(user, Utilities.encrypt(pass))) {
			return "registered";
		}
		
		return "error";
			
	}

}
