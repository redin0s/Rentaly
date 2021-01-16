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
	/*
	@GetMapping("/register")
	public String registerWithError(@RequestParam String user, @RequestParam String error) {
		//TODO set user textArea as RequestParam user 
		
		if (error.equals("existing")) {
			//TODO existing user, maybe login?
		}
		else if (error.equals("generic")) {
			//TODO pls try again
		}
		
		return "register";
	}

	@PostMapping("doRegister")
	public String doRegister(@RequestParam String user, @RequestParam String pass) {			
		
		UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();
		
		//TODO how to manage errors
		
		if (userDAO.findUser(user) != null) {
			return "register?error=existing";
		}
		else if (userDAO.registerUser(user, Utilities.encrypt(pass))) {
			return "login?user=" + user + "&registered=true";
		}
		
		return "register?user=" + user + "&error=generic";
			
	}
*/
}
