package ar.edu.itba.paw.webapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException exc) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.warn("User: " + auth.getName()
            + " attempted to access the protected URL: "
            + request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}