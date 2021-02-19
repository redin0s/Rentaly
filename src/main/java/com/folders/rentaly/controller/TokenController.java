package com.folders.rentaly.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.service.token.TokenParser;
import com.folders.rentaly.service.token.commands.TokenCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/validate")
public class TokenController {
    
    @Autowired
    private TokenParser tokenParser;

    @GetMapping("/{token}")
    public ModelAndView token(ModelAndView model, HttpSession session, @PathVariable String token) {

        Optional<TokenCommand> command = tokenParser.parse(token);

        try {
            command.get().execute(model, session);
        }
        catch (Exception e) {
            log.error("error in validation", e);
            model.setViewName("error");
        }

        return model;
    }
}