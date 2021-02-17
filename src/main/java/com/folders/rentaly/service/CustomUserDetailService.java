package com.folders.rentaly.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;
import com.folders.rentaly.service.token.TokenFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RentalyEmailService emailService;

    @Autowired
    private TokenFactory tokenFactory;

    @Value("${website.url}")
    private String url;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = userDAO.findUser(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        UserBuilder ubuilder = org.springframework.security.core.userdetails.User
            .withUsername(user.get().getEmail())
            .password(user.get().getPassword());

        if (user.get().getActive()) {
            ubuilder = ubuilder.authorities("ACTIVE_USER");
        }
        else {
            ubuilder = ubuilder.authorities("INACTIVE_USER");
        }
        
        return ubuilder.build();
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        //TODO move this in rentalymailservice
        emailService.sendMail(user.getEmail(), "Benvenuto in Rentaly", String.format("%s/validate/%s", url, tokenFactory.makeConfirmRegistrationToken(user)));
    }

    public Optional<User> getUser(HttpSession session) {
		SecurityContext s = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		UserDetails ud = (UserDetails) s.getAuthentication().getPrincipal();
		Optional<User> user = userDAO.findUser(ud.getUsername());
		return user;
	}
}
