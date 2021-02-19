package com.folders.rentaly.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.persistence.dao.InsertionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class InsertionController {
    
    @Autowired
	private InsertionDAO insertionDAO;

	@GetMapping(value = "/insertion/{insertionID}")
	public ModelAndView insertionWithID(HttpSession session, @PathVariable("insertionID") Integer insertionID, ModelAndView model) {
		Optional<Insertion> opt = insertionDAO.get(insertionID);
		log.info(String.format("got insertion with id %id", insertionID.intValue()));
		if (opt.isPresent()) {
			model.addObject("insertion", opt.get());
			model.setViewName("insertion");
		} else {
			model.setViewName("error");
		}
		return model;
	}

}