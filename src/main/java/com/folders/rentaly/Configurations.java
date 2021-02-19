package com.folders.rentaly;

import com.folders.rentaly.service.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
                        .antMatchers("/", "/index", "/register", "/login", "/forgot", "/prova/*", "/validate/*", "/css/**", "/js/**",
                                "https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css",
                                "https://www.bing.com/fd/ls/*") // Here are the public paths
                            .permitAll()
                        .antMatchers("/account", "/account/*", "/realty/*")
                            .authenticated()
                        .antMatchers("/realty/*")
                            .hasAuthority("ACTIVE_USER")
                        .antMatchers("/account/sendNewConfirmationEmail")
                            .hasAuthority("INACTIVE_USER");
                // http.addFilter(
                //         .antMatchers("account/*")

                http.formLogin()
                        .loginPage("/login") // set login page
                            .permitAll()
                        .defaultSuccessUrl("/account")
                        .usernameParameter("email")    // parameters taken from                                            
                        .passwordParameter("password") // the form
                    .and()
                        .logout()
                            .logoutUrl("/logout")
                                .permitAll();

                http.authorizeRequests().anyRequest().denyAll();
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

    @Bean FilterRegistrationBean<GetFilter> ajaxFilter() {
        FilterRegistrationBean<GetFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new GetFilter());
        reg.addUrlPatterns("/account/*");

        return reg;
    }
}