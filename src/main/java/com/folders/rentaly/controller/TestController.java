package com.folders.rentaly.controller;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.RealtyRepository;
import com.folders.rentaly.persistence.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/prova")
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RealtyRepository realtyRepository;

	@GetMapping("/prova")
	public @ResponseBody Iterable<User> prova() {
		Iterable<User> us = userRepository.findAll();

		log.info(us.toString());
		log.info(String.format("%d counts\n", userRepository.count()));

		return us;
	}
	
	@GetMapping("/insertion") 
	public String insertion() {
		return "insertion";
	}

	@GetMapping("/realtyInsertion") 
	public String realty() {
		return "realty";
	}

	@GetMapping("/realty")
	public @ResponseBody Iterable<Realty> realties() {
		return realtyRepository.findAll();
	}

	@GetMapping("/model")
	public String provamod(Model model) {
		model.addAttribute("listUsers", userRepository.findAll());

		return "prova";
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
	
}
