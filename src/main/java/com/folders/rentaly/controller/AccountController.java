package com.folders.rentaly.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.Utilities;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.RealtyRepository;
import com.folders.rentaly.persistence.RentRepository;
import com.folders.rentaly.persistence.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.minidev.json.JSONObject;

@Controller
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private RealtyRepository realtyRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/account")           
	public ModelAndView account(ModelAndView model) {
        model.setViewName("account");
		return model;
	}

    @GetMapping("/myRealties")    
	public ModelAndView myRealties(HttpSession session, ModelAndView model) {
        User user = Utilities.getUser(session);
        List<Realty> realties = realtyRepository.findByOwnerAndDraftFalse(user);
        List<Realty> drafts = realtyRepository.findByOwnerAndDraftTrue(user);
        List<Rent> rents = rentRepository.findByRealty_OwnerAndEndGreaterThanEqual(user, LocalDate.now());

        model.setViewName("myrealties");
        model.addObject("realties", realties);
        model.addObject("drafts", drafts);
        model.addObject("rents", rents);
        log.info(String.format("got myrealties for owner %s", user.getEmail()));
        if(rents.size() == 0) {
            log.info("no rents here");
        }
        return model;
    }
    

    @PostMapping(value = "/myRealties/doAddHolder", consumes={"application/json"})
    @ResponseBody
    public ResponseEntity<String> doAddHolder(HttpSession session, @RequestBody JSONObject content) {
        log.info(content.toString());
        try {
            User holder = userRepository.findByEmail(content.getAsString("user_email"));
            // User user = Utilities.getUser(session);
            Realty realty = realtyRepository.findById(Integer.valueOf(content.getAsNumber("realty_id").intValue())).get();
            Rent rent = new Rent();
            rent.setRealty(realty);
            rent.setHolder(holder);
            rent.setCost(content.getAsNumber("cost").intValue());
            rent.setStart(LocalDate.parse(content.getAsString("start"),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate end = rent.getStart();
            end = end.plusMonths(content.getAsNumber("duration").intValue());
            // log.info("start: " + start + start.toString());
            // log.info("end: " + end + end.toString());
            rent.setEnd(end);
            rentRepository.save(rent);
            return new ResponseEntity<String>("success", HttpStatus.OK);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/myRents")    
	public ModelAndView myRents(HttpSession session, ModelAndView model) {
        User user = Utilities.getUser(session);
        List<Rent> rents = rentRepository.findByHolderAndEndGreaterThanEqual(user, LocalDate.now());
        model.setViewName("myrents");
        model.addObject("rents", rents);
        log.info(String.format("got myrents from holder %s", user.getEmail()));
        if(rents.size() == 0) {
            log.info("no rents here");
        }
        return model;
    }
}

/*
CREATE TRIGGER addingHolderToRealty ON rent BEFORE INSERT AS
BEGIN
    DECLARE @max_holders INT
    DECLARE @current_holders INT
    SELECT @max_holders = max_holders, @current_holders = current_holders
    FROM realty
    WHERE id = NEW.realty

    IF @current_holders = @max_holders THEN
        SIGNAL SQLSTATE '45000';
    ELSE
        UPDATE realty
        SET current_holders = current_holders + 1
        WHERE id = NEW.realty;
    END IF;
END;
*/