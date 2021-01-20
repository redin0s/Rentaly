package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/login")
	public String cleanLogin() {
		return "login";
	}
	
	@PostMapping(value = "/doLogin", consumes={"application/json"})
	@ResponseBody
    public ResponseEntity<String> doLogin(HttpSession session, @RequestBody User user) {
		System.out.println("login " + user);			
		
		User foundUser = userRepository.findByEmail(user.getEmail());

		if(user.getEmail() == null || user.getEmail().equals("")) {
            return ResponseEntity.badRequest().body("missingemail");
        }
        else if(user.getPassword() == null || user.getPassword().equals("")) {
            return ResponseEntity.badRequest().body("missingpassword");
        }
        else if (foundUser == null) {
            return ResponseEntity.badRequest().body("notexisting");
		}
		else if (foundUser.getPassword().equals(Utilities.encrypt(user.getPassword(), user.getEmail()))) {
            session.setAttribute("logged", user.getEmail());
			return new ResponseEntity<>("succes", HttpStatus.OK);
		}

        return ResponseEntity.badRequest().body("error");
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "index";
	}
	
}