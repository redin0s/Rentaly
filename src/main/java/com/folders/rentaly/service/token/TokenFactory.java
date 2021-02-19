package com.folders.rentaly.service.token;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.folders.rentaly.model.User;
import com.folders.rentaly.model.Rent;

import com.auth0.jwt.JWT;

@Service
public class TokenFactory {

    @Autowired
    private TokenParser tokenParser;
    
    @Value("${token.registration.expiration}")
    private Long registrationExpiration;

    @Value("${token.forgot_password.expiration}")
    private Long forgotExpiration;

    @Value("${token.rent_add_holder.expiration}")
    private Long rentExpiration;

    @Value("${token.change_email.expiration}")
    private Long changeEmailExpiration;

    public String makeConfirmRegistrationToken(User userToConfirm) {

        return  JWT.create()
                    .withSubject("rentaly")
                    .withClaim("type","registration")
                    .withClaim("user", userToConfirm.getEmail())
                    .withExpiresAt(Date.from(Instant.now().plus(registrationExpiration,ChronoUnit.HOURS)))
                    .sign(tokenParser.getAlgorithm());
    }

    public String makeForgotPasswordToken(User userWhoForgot) {
        return  JWT.create()
                    .withSubject("rentaly")
                    .withClaim("type", "password")
                    .withClaim("user", userWhoForgot.getEmail())
                    .withClaim("pass", userWhoForgot.getPassword())
                    .withExpiresAt(Date.from(Instant.now().plus(forgotExpiration,ChronoUnit.HOURS)))
                    .sign(tokenParser.getAlgorithm());
    }

    public String makeRentAddHolderToken(String email, Rent rent) {

        return  JWT.create()
                    .withSubject("rentaly")
                    .withClaim("type", "holder")
                    .withClaim("user", email)
                    .withClaim("rent", rent.getId())
                    .withExpiresAt(Date.from(Instant.now().plus(rentExpiration,ChronoUnit.HOURS)))
                    .sign(tokenParser.getAlgorithm());
    }

    public String makeChangeEmailToken(User user, String newEmail) {
        return  JWT.create()
                    .withSubject("rentaly")
                    .withClaim("type", "changeEmail")
                    .withClaim("user", user.getEmail())
                    .withClaim("newEmail", newEmail)
                    .withExpiresAt(Date.from(Instant.now().plus(changeEmailExpiration,ChronoUnit.HOURS)))
                    .sign(tokenParser.getAlgorithm());
    }
}