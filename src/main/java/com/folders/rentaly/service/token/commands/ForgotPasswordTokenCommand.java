package com.folders.rentaly.service.token.commands;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

//TODO IMPLEMENT EVERYTHING
public class ForgotPasswordTokenCommand extends TokenCommand {
    
    private String userWhoForgot;
    private String forgottenPassword;

    public ForgotPasswordTokenCommand(Boolean isExpired, String userWhoForgot, String forgottenPassword) {
        super(isExpired);
        this.userWhoForgot = userWhoForgot;
        this.forgottenPassword = forgottenPassword;
    }

    @Override
    protected void executeNotExpired(ModelAndView model, HttpSession session) {
        //TODO confirm email & change password (-> return to login)
    }

}