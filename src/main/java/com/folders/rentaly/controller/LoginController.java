package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.DBManager;
import com.folders.rentaly.persistence.dao.UserDAO;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String cleanLogin() {
		return "login";
	}
	
	@PostMapping("/doLogin")
	@ResponseBody
    public ResponseEntity<String> doLogin(HttpSession session, @RequestBody User user) {
	//public ServiceResponse<?> doLogin(@RequestBody User user) {
		System.out.println("login " + user);			
		
		String response = null;
		UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();
		User foundUser = userDAO.findUser(user.getUsername());

		if(user.getUsername() == null || user.getUsername().equals("")) {
            response = "missingusername";
        }
        else if(user.getPassword() == null || user.getPassword().equals("")) {
            response = "missingpassword";
        }
        else if (foundUser == null) {
			response = "notexisting";
		}
		else if (foundUser.getPassword().equals(Utilities.encrypt(user.getPassword()))) {
			response = "success";
            session.setAttribute("logged", user.getUsername());
		}
		else {
			response = "error";
		}
		//return new ServiceResponse<User>(response, foundUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "index";
	}
	
}