package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.UserRepository;

@Controller
public class LoginController {
	
	@GetMapping("/login")           
	public String login() {
		return "login";
	}
	
	// @GetMapping("/logout")
	// public String logout(HttpSession session) {
	// 	session.invalidate();
		
	// 	return "index";
	// }
	
}