package com.folders.rentaly.service.token;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.folders.rentaly.persistence.dao.RentDAO;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.RentalyEmailService;
import com.folders.rentaly.service.token.commands.ChangeEmailTokenCommand;
import com.folders.rentaly.service.token.commands.ForgotPasswordTokenCommand;
import com.folders.rentaly.service.token.commands.RegistrationTokenCommand;
import com.folders.rentaly.service.token.commands.RentAddHolderTokenCommand;
import com.folders.rentaly.service.token.commands.TokenCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenParser {

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RentDAO rentDAO;

    @Autowired
    private RentalyEmailService emailService;

    public TokenParser(@Value("${token.jwt.algorithm}") String algo, @Value("${token.jwt.secret}") String secret)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        algorithm = (Algorithm) Algorithm.class.getMethod(algo, String.class).invoke(null, secret);
        verifier = JWT
                    .require(algorithm)
                    .build();

    }

	public Optional<TokenCommand> parse(String token) {
        Optional<TokenCommand> command = Optional.empty();

        try {
            DecodedJWT dec = JWT.decode(token);
            boolean expired = false;
            try {
                verifier.verify(dec);
            } catch (AlgorithmMismatchException e) {
                log.info("received not valid token " + token);
            } catch (TokenExpiredException e) {
                expired = true;
            }
            switch (dec.getClaim("type").asString()) {
                case "registration":
                    command = Optional.of(new RegistrationTokenCommand(expired, dec.getClaim("user").asString(), userDAO));
                    break;
    
                case "password":
                    command = Optional.of(new ForgotPasswordTokenCommand(expired,dec.getClaim("user").asString(), dec.getClaim("pass").asString(), userDAO));
                    break;

                case "holder":
                    command = Optional.of(new RentAddHolderTokenCommand(expired, dec.getClaim("user").asString(), dec.getClaim("rent").asInt(), userDAO, rentDAO, emailService));
                    break;

                case "changeEmail":
                    command = Optional.of(new ChangeEmailTokenCommand(expired, dec.getClaim("user").asString(), dec.getClaim("newEmail").asString(), userDAO));
                    break;

                default:
                    break;
            }
    

        }
        catch (JWTDecodeException e) {
            log.info("received a non JWT token");
        }

        return command;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}