package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.CustomUserDetailService;
import com.folders.rentaly.persistence.UserRepository;

@Controller
public class RegisterController {
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@GetMapping({"/" , "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping(value = "/register", consumes={"application/json"})
	@ResponseBody
    public ResponseEntity<String> register(HttpSession session, @RequestBody @Valid User user) {	
		String response = "error";
		HttpStatus status = HttpStatus.CONFLICT;
		log.info(user.toString());

		if (userRepository.findByEmail(user.getEmail()) != null) {
			response = "existing user";
		}
		else {
			try {
				customUserDetailService.saveUser(user);
				response = "success";
				status = HttpStatus.OK;
				// session.setAttribute("logged", user.getEmail());
			}
			catch (Exception e) {
				response = "error";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
        return new ResponseEntity<String>(response, status);
	}

}
