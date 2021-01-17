package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.folders.rentaly.persistence.DBManager;

@Controller
public class TestController {
	
	@GetMapping("/prova")
	public String prova(HttpSession session, Model model) {
		//ModelAndView model = new ModelAndView("prova");
		
		//DBManager.getInstance().
		
		model.addAttribute("listUsers", DBManager.getInstance().getUserDAOJDBC().findAll());
		
		//return model.getViewName();
		return "prova";
	}
	
	@GetMapping("/a") 
	public String insertion() {
		return "insertion";
	}
}
