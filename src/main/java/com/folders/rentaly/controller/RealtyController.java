package com.folders.rentaly.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.persistence.RealtyRepository;
import com.folders.rentaly.persistence.UserRepository;

@Controller
public class RealtyController {
	private static final Logger log = LoggerFactory.getLogger(RealtyController.class);

	@Autowired
	private RealtyRepository realtyRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/realty/new")
	public String newRealty(HttpSession session, ModelAndView model) {
		Realty r = new Realty();
		r.setOwner(Utilities.getUser(session, userRepository));
		model.setViewName("realty");
		model.addObject("realty", r);
		return model.getViewName();
	}

	@GetMapping(value = "/realty/{realtyID}")
	public ModelAndView realty(HttpSession session, @PathVariable("realtyID") Integer realtyID, ModelAndView model) {
		Optional<Realty> opt = realtyRepository.findByIdAndOwner(realtyID, Utilities.getUser(session, userRepository));
		if (opt.isPresent()) {
			model.addObject("realty", opt.get());
			model.setViewName("realty");
		} else {
			model.setViewName("error");
		}
		return model;
	}

	@PostMapping(value = "/doSaveDraft", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<String> doSaveDraft(HttpSession session, @RequestBody Realty realty) {
		log.info("save draft " + realty.getId());
		try {
			realty.setOwner(Utilities.getUser(session, userRepository));
			// realty.setIs_draft(true);
			realtyRepository.save(realty);

			return new ResponseEntity<>("succes", HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.getStackTrace().toString());
		}

		return ResponseEntity.badRequest().body("error");
	}

	@PostMapping(value = "/doSaveRealty", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<String> doSaveRealty(HttpSession session, @Valid @RequestBody Realty realty) {
		log.info("save realty " + realty.getId());
		try {
			realty.setOwner(Utilities.getUser(session, userRepository));
			// realty.setIs_draft(false);
			realtyRepository.save(realty);

			return new ResponseEntity<>("succes", HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.getStackTrace().toString());
		}

		return ResponseEntity.badRequest().body("error");
	}
}
