package com.folders.rentaly.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.RentalyEmailService;

@Slf4j
@Controller
public class AuthenticationController {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CustomUserDetailService customUserDetailService;
    
	@Autowired    
    private RentalyEmailService emailService;

	@GetMapping({"/" , "/index"})
	public String index() {
		return "index";
	}

	@GetMapping("/login")           
	public String login() {
		return "login";
	}

	@GetMapping("/forgot")
	public String forgot() {
		return "forgotpassword";
	}

	@PostMapping("/forgot")
	public ResponseEntity<String> pforgot() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping(value = "/register", consumes = {"application/json"})
	@ResponseBody
    public ResponseEntity<String> register(HttpSession session, @RequestBody @Valid User user) {	
		String response = "error";
		HttpStatus status = HttpStatus.CONFLICT;
		log.info(user.toString());

		if (userDAO.findUser(user.getEmail()).isPresent()) {
			response = "existing user";
		}
		else {
			try {
				customUserDetailService.registerUser(user);

				response = "success";
				status = HttpStatus.OK;
			}
			catch (Exception e) {
				response = "error";
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				log.error(e.getMessage());
			}
		}
        return new ResponseEntity<String>(response, status);
	}

	@PostMapping(value = "/forgotPassword", consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity<String> doSendForgotPasswordToken(@RequestBody JSONObject content) {
        String email = content.getAsString("email");
		Optional<User> us = userDAO.findUser(email);
		if (us.isPresent()) {
			emailService.sendForgotPasswordEmail(us.get());
            return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/newPassword", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> doSetNewPassword(HttpSession session, @RequestBody JSONObject content) { 
		String tokenEmail = (String) session.getAttribute("tokenemail");
		String inputEmail = content.getAsString("email");
		String newPassword = content.getAsString("newPassword");
		session.removeAttribute("tokenmail");
		if(tokenEmail.equalsIgnoreCase(inputEmail) && newPassword != null)
        	if(customUserDetailService.updatePassword(tokenEmail, newPassword))
        	    return new ResponseEntity<String>("success", HttpStatus.OK);
        return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
