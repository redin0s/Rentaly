package com.folders.rentaly.service.token.commands;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;

import org.springframework.web.servlet.ModelAndView;

public class RegistrationTokenCommand extends TokenCommand {
    
    private UserDAO userDAO;

    private String userEmail;

    public RegistrationTokenCommand(Boolean isExpired, String userEmail, UserDAO userDAO) {
        super(isExpired);
        this.userEmail = userEmail;
        this.userDAO = userDAO;
    }
    
    @Override
    protected void executeNotExpired(ModelAndView model, HttpSession session) {
        //confirm registration
        Optional<User> u = userDAO.findUser(userEmail);
        if (u.isPresent() && !u.get().getActive()) {
            u.get().setActive(true);
            userDAO.save(u.get());

            session.invalidate();

            model.addObject("type", "alert-dark");
            model.addObject("message", "Il tuo account è stato confermato. <a href=\"/account\">Accedi</a>");
            model.addObject("title", "Bentornato, " + u.get().getEmail() + "!");
        }
        model.setViewName("error");
    }

}