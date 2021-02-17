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
        }
        model.setViewName("activated");
        // TODO pagina di info "activated" con "il tuo account è stato confermato",
        // timer 5s e redirect al login
        model.addObject("username", userEmail);
    }

}