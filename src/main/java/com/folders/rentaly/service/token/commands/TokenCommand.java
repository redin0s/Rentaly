package com.folders.rentaly.service.token.commands;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

public abstract class TokenCommand {

    Boolean isExpired;

    public TokenCommand(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    protected void executeExpired(ModelAndView model, HttpSession session) {
		model.setViewName("error");
		model.addObject("type", "alert-danger");
		model.addObject("title", "Errore!");
        model.addObject("error", "Questo link è scaduto, ti preghiamo di tornare alla pagina iniziale.");
    }

    protected abstract void executeNotExpired(ModelAndView model, HttpSession session);
    // protected abstract void execute(ModelAndView model, HttpSession session);

    public void execute(ModelAndView model, HttpSession session) {
        if (isExpired) {
            executeExpired(model,session);
        }
        else {
            executeNotExpired(model,session);
        }
    }
}