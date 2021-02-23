package com.folders.rentaly.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.RentalyEmailService;
import com.folders.rentaly.service.storage.StorageService;
import com.folders.rentaly.service.token.TokenFactory;
import com.folders.rentaly.service.token.TokenParser;
import com.folders.rentaly.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/prova")
public class TestController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RealtyDAO realtyDAO;

	@GetMapping("/prova")
	public @ResponseBody User prova() {
		Optional<User> us = userDAO.findUser("me@muraca.dev");

		log.info(us.toString());

		return us.get();
	}

	@GetMapping("/insertion") 
	public String insertion() {
		return "insertion";
	}

	@GetMapping("/realtyInsertion") 
	public String realty() {
		return "realty";
	}

	@GetMapping("/exit")
	public void exitfun() {
		System.out.println("Shutdown");
		System.exit(10);
	}

	@GetMapping("/dashboard")
	public String provadash() {
		return "dashboard";
	}
	
	@GetMapping("/search")
	public String provasearch() {
		return "search";
	}

	@Autowired
    private RentalyEmailService emailService;

	@GetMapping("/mail")
	public String sendmail() {
		emailService.sendEmail("mmuraca247@gmail.com", "Test", "ido");
		return "myRealties";
	}

	@Autowired
	private TokenFactory tokenFactory;

	@Autowired
	private CustomUserDetailService userDetailService;

	@GetMapping("/token")
	public ResponseEntity<String> getToken(HttpSession session) {
		return new ResponseEntity<>(tokenFactory.makeConfirmRegistrationToken(userDetailService.getUser(session).get()), HttpStatus.OK);
	}

	@Autowired
	TokenParser tokenParser;

	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam String token) {
		try {
			JWTVerifier jwt = JWT.require(tokenParser.getAlgorithm()).build();
			DecodedJWT tok = jwt.verify(token);
			return new ResponseEntity<String>(tok.getSubject(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/upload")
	public String uploadget() {

		return "upload";
	}

	@Autowired
	StorageService storageService;

	@PostMapping("/upload")
	public String uploadpost(@RequestParam("file") MultipartFile[] file) throws Exception {
		for (MultipartFile multipartFile : file) {
			storageService.save(multipartFile,1);
		}
		return "index";
	}

	// @GetMapping("/image/{id}/{imagename}")
	// public ResponseEntity<byte[]> testphoto(@PathVariable Integer id, @PathVariable String imagename) throws IOException {
	// 	InputStream f = new FileInputStream(storageService.getAllById(1).get(0).toFile());
	// 	MediaType.parseMediaType(input);
	// 	return ResponseEntity.ok().body(new InputStreamResource(f));
	// }

	@GetMapping("/imagepr")
	public ResponseEntity<String> provaimg() throws IOException {
		// return ResponseEntity.ok().body(storageService.getAllById(1).get(0).toString());
		return ResponseEntity.ok().body("<html><img src='/pictures/1/1614015637090'/></html>");
	}

}
