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
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.token.commands.ForgotPasswordTokenCommand;
import com.folders.rentaly.service.token.commands.RegistrationTokenCommand;
import com.folders.rentaly.service.token.commands.RentAddHolderTokenCommand;
import com.folders.rentaly.service.token.commands.TokenCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenParser {

    private static final Logger log = LoggerFactory.getLogger(TokenParser.class);

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Autowired
    private UserDAO userDAO;

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
                    command = Optional.of(new RegistrationTokenCommand(expired, dec.getClaim("user").asString(),userDAO));
                    break;
    
                case "password":
                    command = Optional.of(new ForgotPasswordTokenCommand(expired,dec.getClaim("user").asString(), dec.getClaim("pass").asString()));
                    break;

                case "holder":
                    command = Optional.of(new RentAddHolderTokenCommand(expired, dec.getClaim("holder").asString(), dec.getClaim("realty").asInt()));
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