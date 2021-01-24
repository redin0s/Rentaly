package com.folders.rentaly;

import com.folders.rentaly.persistence.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requiresChannel()
            .anyRequest()
            .requiresSecure();

        /////// USE THIS ONLY FOR TESTING PURPOSES
        http 
            .authorizeRequests()
            .anyRequest()
            .permitAll();
        http
            .cors().disable();
        http
            .csrf().disable();
        
        // TODO
        // http
        //     .authorizeRequests()
        //     .antMatchers("/", "/index", "/register") //Here are the public paths
        //         .permitAll()
        //     .antMatchers("/account")    //Here only the "require authentication"
        //         .authenticated();
            

        http
            .formLogin()
                .loginPage("/login")            //set login page
                .permitAll()
                .defaultSuccessUrl("/account")
                .usernameParameter("email")     //parameters taken from the form
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}