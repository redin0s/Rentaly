package com.folders.rentaly.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.persistence.dao.InsertionDAO;
import com.folders.rentaly.service.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class InsertionController {
    
    @Autowired
	private InsertionDAO insertionDAO;
	
	@Autowired
	private StorageService storageService;

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


	@GetMapping(name = "/images", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public @ResponseBody BufferedImage getImage() throws IOException {
		List<Path> images = storageService.getAllById(3);
		InputStream is = new FileInputStream(images.get(0).getFileName().toFile());
		// response.getOutputStream().

		// response.getOutputStream().
		// return ImageIO.read(is);
		return ImageIO.read(is);
	}

}