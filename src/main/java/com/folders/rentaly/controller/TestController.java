package com.folders.rentaly.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.RentalyEmailService;
import com.folders.rentaly.service.token.TokenFactory;
import com.folders.rentaly.service.token.TokenParser;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/prova")
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RealtyDAO realtyDAO;

	@GetMapping("/prova")
	public @ResponseBody User prova() {
		Optional<User> us = userDAO.findUser("me@muraca.dev");

		log.info(us.toString());

		return us.get();
	}

	@GetMapping("/searchi")
	@ResponseBody 
	public List<Realty> search(@RequestParam Float lat, @RequestParam Float lon) {
		return realtyDAO.findClosestToPoint(lat, lon);
	}
	
	@GetMapping("/insertion") 
	public String insertion() {
		return "insertion";
	}

	@GetMapping("/realtyInsertion") 
	public String realty() {
		return "realty";
	}

	@GetMapping("/exit")
	public void exitfun() {
		System.out.println("Shutdown");
		System.exit(10);
	}

	@GetMapping("/dashboard")
	public String provadash() {
		return "dashboard";
	}
	
	@GetMapping("/search")
	public String provasearch() {
		return "search";
	}

	@Autowired
    private RentalyEmailService emailService;

	@GetMapping("/mail")
	public String sendmail() {
		emailService.sendMail("mmuraca247@gmail.com", "Test", "ido");
		return "myRealties";
	}

	@Autowired
	private TokenFactory tokenFactory;

	@Autowired
	private CustomUserDetailService userDetailService;

	@GetMapping("/token")
	public ResponseEntity<String> getToken(HttpSession session) {
		return new ResponseEntity<>(tokenFactory.makeConfirmRegistrationToken(userDetailService.getUser(session).get()), HttpStatus.OK);
	}

	@Autowired
	TokenParser tokenParser;

	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam String token) {
		try {
			JWTVerifier jwt = JWT.require(tokenParser.getAlgorithm()).build();
			DecodedJWT tok = jwt.verify(token);
			return new ResponseEntity<String>(tok.getSubject(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
}
