package ar.edu.itba.paw.webapp.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOGGER.warn("User: " + auth.getName()
                + " attempted to access the protected URL: "
                + request.getRequestURI());
            Collection<SimpleGrantedAuthority> authorities = createAuthorities(Arrays.asList("VERIFIED"));
            if (!auth.getAuthorities().containsAll(authorities)) {
                response.sendRedirect(request.getContextPath() + "/user/account");
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/");
    }

    private Collection<SimpleGrantedAuthority> createAuthorities(Collection<String> roles){
        return roles.
            stream()
            .map((role) -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }

}