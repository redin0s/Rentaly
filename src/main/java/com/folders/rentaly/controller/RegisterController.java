package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		if (plsRegister(user, pass)) {
			return "login";
		}else {
			return "registerError";
		}
	}

	private boolean plsRegister(String username, String password) {
		if (username.equals("admin") && password.equals("admin")) {
			return true;
		}
		return false;
	}
}
