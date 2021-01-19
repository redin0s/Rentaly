package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.folders.rentaly.model.Realty;

@Controller
public class RealtyController {

	@GetMapping("/realty") 
	public String realty(HttpSession session, @RequestBody Realty realty) {
		try {
			// String email = session.getAttribute("logged");
			//if realtyID == newRealty
			//create new Realty for user
			//if exists realtyID in user-realties
			return "realty";
			//else
			// 		System.out.println("requested realty mismatches owner");
			// return "error";
		}
		catch (IllegalStateException e) {
			// if (DEBUG)
			// 		System.out.println("realty request for user not logged");
		}
		return "index";
	}

	@PostMapping("/createRealty")
	@ResponseBody
	public ResponseEntity<String> doRegister(HttpSession session, @RequestBody Realty realty) {
		// public ServiceResponse<?> doRegister(@RequestBody User user) {
		System.out.println("create realty " + realty);

		String response = null;
		// UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();

		if (realty.getLocation() == null || realty.getLocation().equals("")) {
			response = "invalidlocation";
		}
		else if (realty.getMax_holders() <= 0) {
			response = "invalidHolders";
		}
		else if (realty.getType() == null || realty.getType().equals("")) {
			response = "invalidType";
		}
		else if (realty.getSquare_meters() <= 0) {
			response = "invalidsquare_meters";
		}
		else {
			response = "error";
		}
		// return new ServiceResponse<User>(response, user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
