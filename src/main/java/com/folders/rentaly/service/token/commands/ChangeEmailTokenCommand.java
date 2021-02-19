package com.folders.rentaly.service.token.commands;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;

import org.springframework.web.servlet.ModelAndView;

public class ChangeEmailTokenCommand extends TokenCommand {

    private String oldEmail;
    private String newEmail;
    private UserDAO userDAO;

    public ChangeEmailTokenCommand(Boolean isExpired, String oldEmail, String newEmail, UserDAO userDAO) {
        super(isExpired);
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.userDAO = userDAO;
    }

	@Override
	protected void executeNotExpired(ModelAndView model, HttpSession session) {
        try {
            Optional<User> u = userDAO.findUser(oldEmail);
            if (u.isPresent()) {
                u.get().setEmail(newEmail);
                userDAO.save(u.get());

                session.invalidate();

                model.setViewName("error");
                model.addObject("type", "alert-dark");
                model.addObject("message", "la tua email è stata cambiata. <a href=\"/account\">Accedi</a>");
                model.addObject("title", "Congratulazioni " + u.get().getEmail() + "!");
            }
        }
        catch (Exception e) {
            model.setViewName("error");
        }
	}
}