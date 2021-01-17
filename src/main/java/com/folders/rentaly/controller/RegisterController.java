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
public class RegisterController {

	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/index")
	public String explicitIndex() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/doRegister")
	@ResponseBody
    public ResponseEntity<String> doRegister(HttpSession session, @RequestBody User user) {	
	//public ServiceResponse<?> doRegister(@RequestBody User user) {	
        System.out.println("register " + user);

        String response = null;
		UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();

        if(user.getUsername() == null || user.getUsername().equals("")) {
            response = "missingusername";
        }
        else if(user.getPassword() == null || user.getPassword().equals("")) {
            response = "missingpassword";
        }
        // else if (!Utilities.emailCheck(user.getUsername())) {
        //     response = "invalidemail";
        // }
		else if (userDAO.findUser(user.getUsername()) != null) {
			response = "existing";
		}
		else if (userDAO.registerUser(user.getUsername(), Utilities.encrypt(user.getPassword()))) {
			response = "success";
            session.setAttribute("logged", user.getUsername());
		}
		else {
            response = "error";
        }
		//return new ServiceResponse<User>(response, user);	
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
