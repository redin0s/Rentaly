package com.folders.rentaly.service.token.commands;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;

import org.springframework.web.servlet.ModelAndView;

public class ForgotPasswordTokenCommand extends TokenCommand {
    
    private String userWhoForgot;
    private String forgottenPassword;
    
    private UserDAO userDAO;

    public ForgotPasswordTokenCommand(Boolean isExpired, String userWhoForgot, String forgottenPassword, UserDAO userDAO) {
        super(isExpired);
        this.userWhoForgot = userWhoForgot;
        this.forgottenPassword = forgottenPassword;
        this.userDAO = userDAO;
    }

    @Override
    protected void executeNotExpired(ModelAndView model, HttpSession session) {
        //confirm email & change password (-> return to login)
        model.setViewName("newpassword");
        //setta mail
        session.setAttribute("tokenemail", userWhoForgot);
    }

    @Override
    public void execute(ModelAndView model, HttpSession session) {
        if (isExpired || !passwordControl()) {
            executeExpired(model,session);
        }
        else {
            executeNotExpired(model,session);
        }
    }

    private boolean passwordControl() {
        Optional<User> user = userDAO.findUser(userWhoForgot);
        return user.isPresent() && user.get().getPassword().equals(forgottenPassword);
    }
}