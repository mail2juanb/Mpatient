package com.microdiab.mpatient.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authHeader = httpRequest.getHeader("Authorization");
        String usernameHeader = httpRequest.getHeader("X-Auth-Username");
        String rolesHeader = httpRequest.getHeader("X-Auth-Roles");
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        logger.info("=== Incoming Request to mPatient ===");
        logger.info("Method: {}, URI: {}", method, uri);
        logger.info("Authorization Header: {}", authHeader);
        logger.info("X-Auth-Username: {}", usernameHeader);
        logger.info("X-Auth-Roles: {}", rolesHeader);

        chain.doFilter(request, response);
    }
}
