package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@PostMapping("doLogin")
	public String login(@RequestParam String user, @RequestParam String pass) {
		System.out.println(user);
		System.out.println(pass);				
		
		if (plsLogin(user, pass)) {
			return "index";
		}else {
			return "loginError";
		}
	}

	private boolean plsLogin(String username, String password) {
		if (username.equals("admin") && password.equals("admin")) {
			return true;
		}
		return false;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
