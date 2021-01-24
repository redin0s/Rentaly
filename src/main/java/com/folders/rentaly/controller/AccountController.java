package com.folders.rentaly.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.UserRepository;
import com.folders.rentaly.persistence.InsertionRepository;
import com.folders.rentaly.persistence.RealtyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
	private UserRepository userRepository;

    @Autowired
    private RealtyRepository realtyRepository;
    
    @Autowired
	private InsertionRepository insertionRepository;

    @GetMapping("/account")           
	public String account() {
		return "account";
	}

    @GetMapping("/myRealties")    
	public ModelAndView myRealties(HttpSession session, ModelAndView model) {
        List<Realty> realties = realtyRepository.findByOwner(Utilities.getUser(session, userRepository));
        List<Insertion> insertions = insertionRepository.findByRealtyIn(realties);

        model.setViewName("account");
        model.addObject("realtiesList", realties);
        model.addObject("insertionList", insertions);

        return model;
    }
    
}