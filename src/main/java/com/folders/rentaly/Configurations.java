package com.folders.rentaly;

import com.folders.rentaly.persistence.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configurations {
	@Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public WebSecurityConfigurerAdapter securityConfigs() {
        return new WebSecurityConfigurerAdapter(){
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(customUserDetailService);
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.requiresChannel().anyRequest().requiresSecure();

                /////// USE THIS ONLY FOR TESTING PURPOSES
                // http
                // .authorizeRequests()
                // .anyRequest()
                // .permitAll();
                // http.cors().disable();
                // http.csrf().disable();

                http.authorizeRequests()
                        .antMatchers("/", "/index", "/register", "/login", "/prova/*",
                                "https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css",
                                "https://www.bing.com/fd/ls/*") // Here are the public paths
                        .permitAll().antMatchers("/account", "/myRealties", "/realty/*", "/myRents") // Here only the
                                                                                                     // "require
                                                                                                     // authentication"
                        .authenticated();

                http.formLogin().loginPage("/login") // set login page
                        .permitAll().defaultSuccessUrl("/account").usernameParameter("email") // parameters taken from
                                                                                              // the form
                        .passwordParameter("password").and().logout().logoutUrl("/logout").permitAll();
            }
        };
    }

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}