package com.folders.rentaly.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RentalyEmailService emailService;

    private String encode(String clear){
        return bCryptPasswordEncoder.encode(clear);
    }

    private boolean matches(String clear, String hashed) {
        return bCryptPasswordEncoder.matches(clear, hashed);
    }

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

    public void registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        emailService.sendRegistrationEmail(user);
    }

    public Optional<User> getUser(HttpSession session) {
		SecurityContext s = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		UserDetails ud = (UserDetails) s.getAuthentication().getPrincipal();
		Optional<User> user = userDAO.findUser(ud.getUsername());
		return user;
	}

    public boolean changePassword(HttpSession session, String oldPassword, String newPassword) {
        Optional<User> user = getUser(session);
        if (user.isPresent() && matches(oldPassword,user.get().getPassword())) {
            user.get().setPassword(encode(newPassword));
            userDAO.save(user.get());
            log.info("Successfully changed password for user "+ user.get().getEmail());
            return true;
        }
        log.info("Unable to change password for user "+ user.get().getEmail());
        return false;
    }

    public boolean updatePassword(String email, String newPassword) {
        Optional<User> user = userDAO.findUser(email);
        if (user.isPresent()) {
            user.get().setPassword(encode(newPassword));
            userDAO.save(user.get());
            return true;
        }
        return false;
    }
}
