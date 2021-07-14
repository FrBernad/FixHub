package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.token.SessionRefreshToken;
import ar.edu.itba.paw.models.user.Roles;
import ar.edu.itba.paw.models.user.User;
import io.jsonwebtoken.*;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JwtUtil {

    public final static String SESSION_REFRESH_TOKEN_COOKIE_NAME = "SESSION_REFRESH_TOKEN";

    private String secretKey;

    private final static int TOKEN_EXPIRATION_MILLIS = 1000 * 20 * 60;  //20 mins duration

    public JwtUtil(Resource secretKeyRes) throws IOException {
        this.secretKey = FileCopyUtils.copyToString(new InputStreamReader(secretKeyRes.getInputStream()));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public UserDetails decodeToken(String token) {
        try {

            final Claims claims = extractAllClaims(token);

            final String username = claims.getSubject();

            final Collection<GrantedAuthority> authorities =
                getAuthorities(claims.get("roles", String.class));

            return new org.springframework.security.core.userdetails.User(username, "", authorities);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }

    public String generateToken(User userDetails) {
        Claims claims = Jwts.claims();

        claims.put("roles", serializeRoles(userDetails.getRoles()));

        return "Bearer " + Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_MILLIS))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public NewCookie generateSessionRefreshCookie(SessionRefreshToken sessionRefreshToken) {
        return new NewCookie(SESSION_REFRESH_TOKEN_COOKIE_NAME,
            sessionRefreshToken.getValue(),
            "/",
            null,
            Cookie.DEFAULT_VERSION,
            "Session Refresh Token",
            (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), sessionRefreshToken.getExpirationDate()),
            null,
            false,
            true);
    }


    public NewCookie generateDeleteSessionCookie() {
        return new NewCookie(SESSION_REFRESH_TOKEN_COOKIE_NAME,
            null,
            "/",
            null,
            Cookie.DEFAULT_VERSION,
            null,
            0,
            new Date(0),
            false,
            true);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private String serializeRoles(Collection<Roles> roles) {
        final StringBuilder sr = new StringBuilder();

        for (Roles role : roles) {
            sr.append(role.name()).append(' ');
        }

        sr.deleteCharAt(sr.length() - 1);

        return sr.toString();
    }

    private Collection<GrantedAuthority> getAuthorities(String roles) {
        return Arrays.stream(roles.split(" "))
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }


}
