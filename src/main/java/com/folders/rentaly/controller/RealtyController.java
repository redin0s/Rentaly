package com.folders.rentaly.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import com.folders.rentaly.model.Insertion;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.persistence.dao.InsertionDAO;
import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.storage.StorageService;

@Slf4j
@Controller
@RequestMapping(value = "/realty")
public class RealtyController {

	@Autowired
	private RealtyDAO realtyDAO;

	@Autowired
	private InsertionDAO insertionDAO;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private StorageService storageService;

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
			try {
				model.addObject("images", storageService.getAllById(opt.get().getInsertion().getId()));
			} catch (Exception e) {
				log.error("errore in /{realty}", e);
			}
			model.setViewName("realty");
		} else {
			model.setViewName("error");
		}
		return model;
	}

	@PostMapping(value = "/doSaveDraft", consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<String> doSaveDraft(HttpSession session, @RequestPart("files") MultipartFile[] files, @RequestPart("realty") Realty realty) {
		return doXRealty(session, realty, files, true);
	}

	@PostMapping(value = "/doSaveRealty", consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseEntity<String> doSaveRealty(HttpSession session, @RequestPart("files") MultipartFile[] files, @RequestPart("realty") Realty realty) {
		return doXRealty(session, realty, files, false);
	}

	private ResponseEntity<String> doXRealty(HttpSession session, Realty realty, MultipartFile[] files, Boolean draft) {
		log.info("save realty " + realty.toString());
		try {
			realty.setOwner(customUserDetailService.getUser(session).get());
			realty.setDraft(draft);
			Insertion insertion = realty.getInsertion();
			if (realty.getId() != null) {
				//è un immobile presente nel db, quindi potrebbe avere giá una inserzione
				Optional<Realty> opt = realtyDAO.get(realty.getId());
				if (opt.isPresent() && !opt.get().getOwner().equals(customUserDetailService.getUser(session).get())) {
					return ResponseEntity.badRequest().body("error");
				}
				if (opt.isPresent() && opt.get().getInsertion() != null && opt.get().getInsertion().getId() != null) {
					//se l'immobile ha giá una inserzione allora settiamo l'id
					if (insertion == null) {
						insertion = opt.get().getInsertion();
						realty.setInsertion(insertion);
					}
					else {
						insertion.setId(opt.get().getInsertion().getId());
					}
				}
			}	
			if (insertion != null) {
				if (insertion.getIs_visible() == null ||
				   (insertion.getIs_visible() == true && 
						(insertion.getCost() == null || insertion.getCost() == 0)))
				{
					insertion.setIs_visible(false);
				}
				insertionDAO.save(insertion);
				// storageService.saveAll((MultipartFile[])realtywrapper.getFiles().toArray(), insertion.getId());
				storageService.saveAll(files, insertion.getId());
			}
			
			realtyDAO.save(realty);
			
			return new ResponseEntity<>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return ResponseEntity.badRequest().body("error");
	}

}
