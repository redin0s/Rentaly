package com.folders.rentaly.controller;

import javax.servlet.http.HttpSession;

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

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.persistence.InsertionRepository;
import com.folders.rentaly.persistence.RealtyRepository;

@Controller
public class RealtyController {
	private static final Logger log = LoggerFactory.getLogger(RealtyController.class);

	@Autowired
	private RealtyRepository realtyRepository;

	@Autowired
	private InsertionRepository insertionRepository;

	/*
	@RequestMapping(value = "/album", method = RequestMethod.GET)
    public String showAlbums(@Valid @ModelAttribute("album") Album album, Model model) {
		model.addAttribute("albums", this.albumService.tuttiAlbum());
		return "/albumList";
		Model
	}
	*/

	@RequestMapping("/newRealty")
	public String newRealty(HttpSession session) {
		return realty(session, 0);
	}

	@RequestMapping("/realty/{realtyID}") 
	public String realty(HttpSession session, @PathVariable("realtyID") Integer realtyID) {
		try {
			String email = session.getAttribute("logged").toString();
			if (realtyID == 0) {
				Realty r = new Realty();
				Insertion i = new Insertion();
				// session.addParameter("realty", r);
				// session.addParameter("insertion", i);
			}
			else {
				// Realty r = realtyRepository.findById(realtyID);
				// if (r.getOwner().getEmail() == email) {
				// 	Insertion i = insertionRepository.findById(realtyID);
				// 	session.addParameter("realty", r);
				// 	session.addParameter("insertion", i);
				// }
				// else {
				// 	log.info("requested realty " + realtyID.toString() + " mismatches owner");
				// 	return "error";
				// }
			}
			return "realty";
		}
		catch (IllegalStateException e) {
			log.info("realty request for user not logged");
		}
		return "index";
	}

	@PostMapping("/updateRealty")
	@ResponseBody
	public ResponseEntity<String> doRegister(HttpSession session, @RequestBody Realty realty) {
		System.out.println("create realty " + realty);

		String response = null;

		if (realty.getLocation() == null || realty.getLocation().equals("")) {
			response = "invalidlocation";
		}
		else if (realty.getMax_holders() <= 0) {
			response = "invalidHolders";
		}
		else if (realty.getType() == null || realty.getType().equals("")) {
			response = "invalidType";
		}
		else if (realty.getSquare_meters() <= 0) {
			response = "invalidsquare_meters";
		}
		else {
			response = "error";
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
