package com.folders.rentaly;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
public class GetFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        
        if (req.getMethod().equals("GET")) {
            Optional<String> ajax = Optional.ofNullable(req.getHeader("AJAX"));
            if(ajax.isPresent() && ajax.get().equals("true") || req.getRequestURI().equals("/account")) {
                // log.info(ajax.get() + " 30");
                // log.info("come in you GETTER");
                chain.doFilter(request, response);
                return;
            }
        }
        else {
            chain.doFilter(request, response);
            // log.info("come in you POSTER");
            return;
        }
        // log.info(req.getHeaderNames().nextElement());
        // log.info("NYET!");
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(HttpStatus.BAD_REQUEST.value());
    }
    
}
