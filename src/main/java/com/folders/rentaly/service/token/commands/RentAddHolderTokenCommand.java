package com.folders.rentaly.service.token.commands;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.RentalyEmailService;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.Rent;
import com.folders.rentaly.persistence.dao.RentDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class RentAddHolderTokenCommand extends TokenCommand {
    
    private UserDAO userDAO;

    private RentDAO rentDAO;

    @Autowired
    private RentalyEmailService emailService;

    private String holderEmail;
    private Integer rentID;

    public RentAddHolderTokenCommand(Boolean isExpired, String holderEmail, Integer rentID) {
        super(isExpired);
        this.holderEmail = holderEmail;
        this.rentID = rentID;
    }

    @Override
    protected void executeExpired(ModelAndView model, HttpSession session) {
        Optional<Rent> r = rentDAO.get(rentID);
        if (r.isPresent() && r.get().getHolder() == null) {
            //fallback to "token expired, sent mail to holder" & send mail to holder
            model.setViewName("error");
            //TODO mail
            model.addObject("error", "Questo link è scaduto, ma non sei ancora stato aggiunto a questo immobile come inquilino. Ti abbiamo inviato un nuovo link, controlla la tua casella di posta elettronica.");
        }
        else {
            super.executeExpired(model, session);
        }
    }

    @Override
    protected void executeNotExpired(ModelAndView model, HttpSession session) {
        //login/register & add holder to rent
        Optional<User> h = userDAO.findUser(holderEmail);
        Optional<Rent> r = rentDAO.get(rentID);
        if (h.isPresent() && r.isPresent()) {
            r.get().setActive(true);
            r.get().setHolder(h.get());
            rentDAO.save(r.get());
        }
    }

}