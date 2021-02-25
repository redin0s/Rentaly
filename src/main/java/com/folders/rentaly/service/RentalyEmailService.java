package com.folders.rentaly.service;

import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;

import com.folders.rentaly.model.Rent;
import com.folders.rentaly.model.User;
import com.folders.rentaly.service.token.TokenFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
 
@Service
@PropertySource(
    "classpath:email.properties"
)
public class RentalyEmailService {

    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String ourmail;

    @Value("${website.url}")
    private String url;

    @Value("${mail.registration.object}")
    private String registrationObject;
    @Value("${mail.registration.content}")
    private String registrationContent;

    @Value("${mail.changeEmail.object}")
    private String changeEmailObject;
    @Value("${mail.changeEmail.content}")
    private String changeEmailContent;

    @Value("${mail.rentaddholder.object}")
    private String rentAddHolderObject;
    @Value("${mail.rentaddholder.content}")
    private String rentAddHolderContent;

    @Value("${mail.forgotpassword.object}")
    private String forgotPasswordObject;
    @Value("${mail.forgotpassword.content}")
    private String forgotPasswordContent;

    @Autowired
    TokenFactory tokenFactory;

    public RentalyEmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);

    }

    public void sendRegistrationEmail(User user) {
        sendEmail(   user.getEmail(),
                    registrationObject,
                    String.format(
                        registrationContent,
                        url,
                        tokenFactory.makeConfirmRegistrationToken(user)));
    }

    public void sendChangeEmail(User user,String newEmail) {
        sendEmail(   newEmail,
                    changeEmailObject,
                    String.format(
                        changeEmailContent,
                        user.getEmail(),
                        url,
                        tokenFactory.makeChangeEmailToken(user, newEmail)));
    }

    public void sendRentAddHolderEmail(Rent rent, String holderEmail) {
        sendEmail(  holderEmail,
                    rentAddHolderObject,
                    String.format(
                        rentAddHolderContent,
                        url,
                        tokenFactory.makeRentAddHolderToken(holderEmail, rent),
                        rent.getRealty().getType(),
                        rent.getRealty().getCity()));
    }

    public void sendForgotPasswordEmail(User user) {
        sendEmail(  user.getEmail(),
                    forgotPasswordObject,
                    String.format(
                        forgotPasswordContent,
                        url,
                        tokenFactory.makeForgotPasswordToken(user))
                    );
    }

    public void sendReportEmail(User user, String title, String content) {
        sendEmail(ourmail,String.format("problem from %s",title, user.getEmail()), String.format("%s\n%s",title,content));
    }
}