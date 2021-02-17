package com.folders.rentaly.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.CheckDAO;
import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.persistence.dao.RentDAO;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.CustomUserDetailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private RealtyDAO realtyDAO;

    @Autowired
    private RentDAO rentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CheckDAO checkDAO;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    
    @GetMapping("")           
	public ModelAndView account(ModelAndView model, HttpSession session) {
        model.setViewName("dashboard");
        User logged = customUserDetailService.getUser(session).get();
        model.addObject("active", logged.getActive());

	    return model;
	}

    @RequestMapping(value="/realties", method=RequestMethod.GET)
    public ModelAndView doGetRealties(ModelAndView model, HttpSession session, @RequestParam Boolean isDraft) {
        model.setViewName("realtiesList");
        model.addObject("realties", realtyDAO.findByOwnerAndDraft(customUserDetailService.getUser(session).get(),isDraft));
        return model;
    } 

    @RequestMapping(value="/realties-rents", method=RequestMethod.GET)
    public ModelAndView doGetRealtiesRents(ModelAndView model, HttpSession session, @RequestParam Boolean isEnded) {
        model.setViewName("rentsList");

        List<Rent> ongoingRents = rentDAO.findByRealty_OwnerAndEndGreaterThanEqual(customUserDetailService.getUser(session).get(), LocalDate.now());
        if (isEnded) {
            List<Rent> rents = rentDAO.findByRealty_Owner(customUserDetailService.getUser(session).get());

            for(Rent r: ongoingRents) {
                rents.remove(r);
            }

            model.addObject("rents", rents);
        }
        else {
            model.addObject("rents", ongoingRents);
        }

        return model;
    } 

    @RequestMapping(value="/realties-checks", method=RequestMethod.GET)
    public ModelAndView doGetRealtiesChecks(ModelAndView model, HttpSession session) {
        model.setViewName("checksList");
        model.addObject("checks", checkDAO.findByRent_Realty_Owner(customUserDetailService.getUser(session).get()));
        return model;
    } 

    @PostMapping(value = "/doAddHolder", consumes={"application/json"})
    @ResponseBody
    public ResponseEntity<String> doAddHolder(HttpSession session, @RequestBody JSONObject content) {
        log.info(content.toString());
        try {
            User holder = userDAO.findUser(content.getAsString("user_email")).get();
            // User user = Utilities.getUser(session);
            Realty realty = realtyDAO.get(Integer.valueOf(content.getAsNumber("realty_id").intValue())).get();
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
            rentDAO.save(rent);
            return new ResponseEntity<String>("success", HttpStatus.OK);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
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