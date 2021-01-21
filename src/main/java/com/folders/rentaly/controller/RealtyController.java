package com.folders.rentaly.controller;

import java.util.List;

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
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.RealtyRepository;
import com.folders.rentaly.persistence.UserRepository;

@Controller
public class RealtyController {
	private static final Logger log = LoggerFactory.getLogger(RealtyController.class);

	@Autowired
	private RealtyRepository realtyRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping({"/realty", "/realty/new"})
	public String newRealty(HttpSession session, ModelAndView model) {
		Realty r = new Realty();
		r.setOwner(userRepository.findById(Integer.parseInt(session.getAttribute("logged").toString())).get());
		model.setViewName("realty");
		model.addObject("realty", r);
		return model.getViewName();
	}

	@GetMapping(value = "/realty/{realtyID}")
	public ModelAndView realty(HttpSession session, @PathVariable("realtyID") String realtyID, ModelAndView model) {
		Realty c = new Realty();
		c.setDisplay_name("test diplay " + realtyID);
		model.setViewName("realty");
		model.addObject("realty", c);
		return model;


		// try {
		// 	Integer id = Integer.parseInt(session.getAttribute("logged").toString());
		// 	List<Realty> realties = realtyRepository.findByOwnerId(id);
		// 	model.setViewName("realty");
		// 	for (Realty r: realties) {
		// 		if (r.getId().toString().equals(realtyID)) { //bad xD
		// 			model.addObject("realty", r);
		// 			// model.addObject("id", r.getId());
		// 			// model.addObject("display_name", r.getOwner());
		// 			// model.addObject("latitude", r.la)
		// 			return model.getViewName();
		// 		}
		// 	}
			
		// 	log.info("requested realty " + realtyID + " not present for owner " + id.toString());
		// 	return "error";
			
		// }
		// catch (IllegalStateException e) {
		// 	log.info("realty request for user not logged");
		// }
		// return "index";
	}

	@PostMapping(value = "/doSaveDraft", consumes={"application/json"})
	@ResponseBody
	public ResponseEntity<String> doSaveDraft(HttpSession session, @RequestBody Realty realty) {
		log.info("save draft " + realty.getId());			
		try {
			realty.setOwner(userRepository.findById(Integer.parseInt(session.getAttribute("logged").toString())).get());
			// realty.setIs_draft(true);
			realtyRepository.save(realty);
			return new ResponseEntity<>("succes", HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.getStackTrace().toString());
		}

        return ResponseEntity.badRequest().body("error");
	}

	@PostMapping(value = "/doSaveRealty", consumes={"application/json"})
	@ResponseBody
	public ResponseEntity<String> doSaveRealty(HttpSession session, @Valid @RequestBody Realty realty) {
		log.info("save realty " + realty.getId());			
		try {
			realty.setOwner(userRepository.findById(Integer.parseInt(session.getAttribute("logged").toString())).get());
			//realty.setIs_draft(false);
			realtyRepository.save(realty);
			return new ResponseEntity<>("succes", HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.getStackTrace().toString());
		}

        return ResponseEntity.badRequest().body("error");
	}
}
