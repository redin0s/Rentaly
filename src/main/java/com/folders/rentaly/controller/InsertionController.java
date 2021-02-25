package com.folders.rentaly.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.InsertionDAO;
import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Slf4j
@Controller
public class InsertionController {
    
    @Autowired
	private InsertionDAO insertionDAO;
	
	@Autowired
	private RealtyDAO realtyDAO;

	@Autowired
	private StorageService storageService;

	@Autowired
	private CustomUserDetailService customUserDetailService;

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


	@PostMapping(value = "/delete", consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity<String> removeimage(HttpSession session, @RequestBody(required = true) String image) throws IOException {
		ResponseEntity<String> response = ResponseEntity.badRequest().build();
		Optional<User> user = customUserDetailService.getUser(session);
		if (user.isPresent()) {
			Pattern pattern = Pattern.compile("pictures/(\\d*)/\\d*");
			Matcher matcher = pattern.matcher(image);
			if(matcher.find()) {
				// log.info(String.format("%d", matcher.groupCount()));
				Path p = Path.of(matcher.group(0));
				File f = p.toFile();
				Integer id = Integer.parseInt(matcher.group(1));
				if (f.exists() && realtyDAO.ownerHasInsertion(user.get(), id)) {
					f.delete();
					response = ResponseEntity.ok().build();
				}
			}
		}


		return response;
	}

	@GetMapping(value = "/insertion-modal")
	public ModelAndView getimagesModal(HttpSession session, ModelAndView model, @RequestParam Integer id) {
		model.setViewName("imagemodal");
		model.addObject("images", storageService.getAllById(id));
		return model;
	}

}