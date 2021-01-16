package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.ServiceResponse;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.DBManager;
import com.folders.rentaly.persistence.dao.UserDAO;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String cleanLogin() {
		return "login";
	}
	/*
	@GetMapping("/login")
	public String loginWithError(@RequestParam String user, @RequestParam String error) {
		//TODO set user textArea as RequestParam user 
		/*
			user
			password
			
			javascript
		*
		if (error.equals("password")) {
			//TODO wrong password
		}
		else if (error.equals("generic")) {
			//TODO pls try again
		}
		else if (error.equals("notfound")) {
			//TODO user not found, maybe register?
		}	
		
		return "login";
	}
	
	@GetMapping("/login")
	public String loginByRegistration(@RequestParam String user, @RequestParam String registered) {
		//TODO set user textArea as RequestParam user
		
		if (registered.equals("true")) {
			//TODO registered successfully
		}
		
		return "login";
	}
	*/
	
	@PostMapping("/doLogin")
	public ResponseEntity<Object> doLogin(@RequestBody User user) {
		System.out.println(user);			
		
		String status = null;
		
		UserDAO userDAO = DBManager.getInstance().getUserDAOJDBC();
		User foundUser = userDAO.findUser(user.getUsername());
		if (foundUser == null) {
			status = "notfound";
		}
		else if (foundUser.getPassword().equals(Utilities.encrypt(user.getPassword()))) {
			status = "success";
		}
		else {
			status = "error";
		}
		
		ServiceResponse<User> response = new ServiceResponse<User>(status, foundUser);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "index";
	}
	
}

/*
<dependencies>
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.18.16</version>
		<scope>provided</scope>
	</dependency>
</dependencies>

package com.javatechie.spring.ajax.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
	
	private String status;
	private T data;

}
*/
