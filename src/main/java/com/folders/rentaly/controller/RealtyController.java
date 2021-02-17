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

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.service.CustomUserDetailService;

@Controller
@RequestMapping(value = "/realty")
public class RealtyController {
	private static final Logger log = LoggerFactory.getLogger(RealtyController.class);

	@Autowired
	private RealtyDAO realtyDAO;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@RequestMapping(value = "/new")
	public ModelAndView newRealty(HttpSession session, ModelAndView model) {
		Realty r = new Realty();
		r.setOwner(customUserDetailService.getUser(session).get());
		r.setDraft(true);
		model.setViewName("realty");
		model.addObject("realty", r);
		return model;
	}

	@GetMapping(value = "/{realtyID}")
	public ModelAndView realty(HttpSession session, @PathVariable("realtyID") Integer realtyID, ModelAndView model) {
		Optional<Realty> opt = realtyDAO.findByIdAndOwner(realtyID, customUserDetailService.getUser(session).get());
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
				Optional<Realty> opt = realtyDAO.get(realty.getId());
				if (opt.isPresent() && !opt.get().getOwner().equals(customUserDetailService.getUser(session))) {
					return ResponseEntity.badRequest().body("error");
				}
			}	
			realty.setOwner(customUserDetailService.getUser(session).get());
			realty.setDraft(draft);
			realtyDAO.save(realty);
			return new ResponseEntity<>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return ResponseEntity.badRequest().body("error");
	}

}
