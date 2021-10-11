package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;


@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    private static final String JWT_HEADER = "X-JWT";
    private static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final AuthorizationType authType = parseAuthorizationType(authHeader);

        if (authHeader == null || authType == null) {
            chain.doFilter(request, response);
            return;
        }

        final String payload = authHeader.split(" ")[1].trim();

        Authentication auth;

        try {
            if (authType == AuthorizationType.BASIC) {
                auth = tryBasicAuthentication(payload, response);
            } else {
                auth = tryBearerAuthentication(payload, response);
            }

            SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

            LOGGER.debug("Populated security context with authorization: {}",auth);
        } catch (AuthenticationException e) {
            LOGGER.debug("{} Setting default unauthorized user", e.getMessage());
        }

        chain.doFilter(request, response);
    }

    private Authentication tryBearerAuthentication(final String payload, final HttpServletResponse response) throws AuthenticationException {

        //Try first with JWT
        UserDetails userDetails = jwtUtil.decodeToken(payload);
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }

        //If JWT is invalid try with refresh token
        final Optional<User> userOpt = userService.getUserByRefreshToken(payload);

        if (!userOpt.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Invalid refresh token.");
        }

        final User user = userOpt.get();
        addAuthorizationHeader(response, user);

        return new UsernamePasswordAuthenticationToken
            (user.getEmail(), user.getPassword(), UserDetailService.getAuthorities(user.getRoles()));
    }

    private Authentication tryBasicAuthentication(final String payload, final HttpServletResponse response) throws AuthenticationException {

        String[] decodedCredentials;

        try {
            decodedCredentials = new String(base64Decoder.decode(payload), StandardCharsets.UTF_8).split(":");
            if (decodedCredentials.length != 2) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid credentials for basic authorization.");
        }

        final String email = decodedCredentials[0];
        final String password = decodedCredentials[1];

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        final User user = userService.getUserByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        addAuthorizationHeader(response, user);
        addSessionRefreshTokenHeader(response, user);

        return auth;
    }

    private void addAuthorizationHeader(final HttpServletResponse response, final User user) {
        response.addHeader(JWT_HEADER, jwtUtil.generateToken(user));
    }

    private void addSessionRefreshTokenHeader(final HttpServletResponse response, final User user) {
        response.addHeader(REFRESH_TOKEN_HEADER, userService.getSessionRefreshToken(user).getValue());
    }

    private AuthorizationType parseAuthorizationType(final String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            return AuthorizationType.BEARER;
        } else if (authHeader.startsWith("Basic ")) {
            return AuthorizationType.BASIC;
        }

        return null;
    }

    private enum AuthorizationType {
        BASIC, BEARER
    }
}
