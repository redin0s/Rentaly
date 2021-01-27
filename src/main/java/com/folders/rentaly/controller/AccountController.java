package com.folders.rentaly.controller;

import java.time.LocalDate;
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
import org.springframework.web.servlet.ModelAndView;

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
	public String account() {
		return "account";
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
        log.info(String.format("got realties from user %s", user.getEmail()));

        return model;
    }
    

    @PostMapping(value = "/doAddHolder", consumes={"application/json"})
    public ResponseEntity<String> doAddHolder(HttpSession session, Integer realty_id, String user_email, Integer cost, LocalDate start, Integer duration) {
        User holder = userRepository.findByEmail(user_email);
        User user = Utilities.getUser(session);
        Realty realty = realtyRepository.findById(realty_id).get();
        Rent rent = new Rent();
        rent.setRealty(realty);
        rent.setHolder(holder);
        rent.setCost(cost);
        rent.setStart(start);
        LocalDate end = start;
        log.info("start: " + start + start.toString());
        log.info("end: " + end + end.toString());
        end.plusMonths(duration);
        rent.setEnd(end);
        return new ResponseEntity<String>("success", HttpStatus.OK);
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