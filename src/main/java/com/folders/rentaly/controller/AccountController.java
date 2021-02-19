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
import com.folders.rentaly.service.RentalyEmailService;

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

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

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

    @Autowired
    private RentalyEmailService emailService;
    
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
            Realty realty = realtyDAO.get(Integer.valueOf(content.getAsNumber("realty_id").intValue())).get();
            Rent rent = new Rent();
            rent.setRealty(realty);
            rent.setHolder(holder);
            rent.setCost(content.getAsNumber("cost").intValue());
            rent.setStart(LocalDate.parse(content.getAsString("start"),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            LocalDate end = rent.getStart();
            end = end.plusMonths(content.getAsNumber("duration").intValue());
            rent.setEnd(end);
            rentDAO.save(rent);
            return new ResponseEntity<String>("success", HttpStatus.OK);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/data", method=RequestMethod.GET)
    public ModelAndView doGetData(HttpSession session, ModelAndView model) {
        User u = customUserDetailService.getUser(session).get();

        LocalDate date = LocalDate.now();
        Integer realtiesCount = realtyDAO.countByOwnerAndDraft(u, false);
        Integer ownRentsCount = rentDAO.countByRealty_OwnerAndEndGreaterThanEqual(u, LocalDate.now());
        Integer ownChecksToPayCount = checkDAO.countByOwnerAndExpireGreaterThanEqual(u, date);

        Integer rentsCount = rentDAO.findByHolderAndEndGreaterThanEqual(u, LocalDate.now()).size();
        Integer checksToPayCount = checkDAO.countByHolderAndExpireGreaterThanEqual(u, date);

        model.addObject("realtiesCount", realtiesCount);
        model.addObject("ownRentsCount", ownRentsCount);
        model.addObject("ownChecksToPayCount", ownChecksToPayCount);
        
        model.addObject("rentsCount", rentsCount);
        model.addObject("checksToPayCount", checksToPayCount);

        model.setViewName("account");
        
        return model;
    }

    @PostMapping(value = "/sendNewConfirmationEmail")
    @ResponseBody
    public ResponseEntity<String> sendNewConfirmationEmail(HttpSession session) {
        User u = customUserDetailService.getUser(session).get();
        emailService.sendRegistrationEmail(u);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/changeEmail", method = RequestMethod.GET)
    public ModelAndView getChangeEmail(ModelAndView model) {
        model.setViewName("changeemail");
        
        return model;
    }

    @PostMapping(value = "/changeEmail")
    @ResponseBody
    public ResponseEntity<String> doChangeEmail(HttpSession session, @RequestBody JSONObject content) {
        String newEmail = content.getAsString("newemail");
        User u = customUserDetailService.getUser(session).get();
        emailService.sendChangeEmail(u, newEmail);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ModelAndView getChangePassword(ModelAndView model) {
        model.setViewName("changepassword");
        
        return model;
    }

    @PostMapping(value = "/changePassword", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> doChangePassword(HttpSession session, @RequestBody JSONObject content) { 
        String oldPassword = content.getAsString("oldPassword");
        String newPassword = content.getAsString("newPassword");
        if (oldPassword != null && newPassword != null && customUserDetailService.changePassword(session, oldPassword, newPassword))
            return new ResponseEntity<String>("success", HttpStatus.OK);
        return new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}