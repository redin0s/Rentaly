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

@Controller
@RequestMapping(value = "/realty")
public class RealtyController {
	private static final Logger log = LoggerFactory.getLogger(RealtyController.class);

	@Autowired
	private RealtyRepository realtyRepository;

	@RequestMapping(value = "/new")
	public ModelAndView newRealty(HttpSession session, ModelAndView model) {
		Realty r = new Realty();
		r.setOwner(Utilities.getUser(session));
		r.setDraft(true);
		model.setViewName("realty");
		model.addObject("realty", r);
		return model;
	}

	@GetMapping(value = "/{realtyID}")
	public ModelAndView realty(HttpSession session, @PathVariable("realtyID") Integer realtyID, ModelAndView model) {
		Optional<Realty> opt = realtyRepository.findByIdAndOwner(realtyID, Utilities.getUser(session));
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
		return doXRealty(session, realty, true);
	}

	@PostMapping(value = "/doSaveRealty", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<String> doSaveRealty(HttpSession session, @RequestBody @Valid Realty realty) {
		return doXRealty(session, realty, false);
	}

	private ResponseEntity<String> doXRealty(HttpSession session, Realty realty, Boolean draft) {
		log.info("save realty " + realty.toString());
		try {
			if (realty.getId() != null) {
				Optional<Realty> opt = realtyRepository.findById(realty.getId());
				if (opt.isPresent() && !opt.get().getOwner().equals(Utilities.getUser(session))) {
					return ResponseEntity.badRequest().body("error");
				}
			}	
			realty.setOwner(Utilities.getUser(session));
			realty.setDraft(draft);
			realtyRepository.save(realty);
			return new ResponseEntity<>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return ResponseEntity.badRequest().body("error");
	}

}
