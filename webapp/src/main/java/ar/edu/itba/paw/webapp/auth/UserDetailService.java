package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.getUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user by the name" + username));

        final Collection<? extends GrantedAuthority> authorities = user.getRoles().
            stream()
            .map((role) -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
