package com.folders.rentaly.controller;


import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.UserRepository;

@Controller
public class RegisterController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping({"/" , "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping(value = "/doRegister", consumes={"application/json"})
	@ResponseBody
    public ResponseEntity<String> doRegister(HttpSession session, @RequestBody User user) {	
        System.out.println("register " + user);

        String response = null;

        if(user.getEmail() == null || user.getEmail().equals("")) {
            response = "invalidemail";
        }
        else if(user.getPassword() == null || user.getPassword().equals("")) {
            response = "missingpassword";
        }
        else if (!Utilities.emailCheck(user.getEmail())) {
            response = "invalidemail";
        }
		else if (userRepository.findByEmail(user.getEmail()) != null) {
			response = "existing";
		}
		else {
			try {
				user.setPassword(Utilities.encrypt(user.getPassword(), user.getEmail()));
				userRepository.save(user);
				response = "success";
				session.setAttribute("logged", user.getEmail());
			}
			catch (Exception e) {
				response = "error";
			}
		}
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
