package com.folders.rentaly;

import javax.annotation.PostConstruct;

import com.folders.rentaly.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticContextInitializer {
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        Utilities.setUserRepository(userRepository);
    }
}
