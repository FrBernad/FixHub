package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationErrorHandler();
    }

    @Bean
    public JwtUtil jwtUtil(@Value("classpath:auth/auth_key.pem") Resource authKey) throws IOException {
        return new JwtUtil(authKey);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and().authorizeRequests()

            //-------- /users route ---------
            .antMatchers(
                "/api/users",
                "/api/users/"
            ).anonymous()


            //-------- /user route ---------
            .antMatchers(HttpMethod.POST,
                "/api/user",
                "/api/user/"
            ).anonymous()
            .antMatchers(HttpMethod.GET,
                "/api/user",
                "/api/user/"
            ).authenticated()
            .antMatchers(HttpMethod.PUT,
                "/api/user",
                "/api/user/"
            ).authenticated()
            .antMatchers(HttpMethod.DELETE,
                "/api/user/refreshSession",
                "/api/user/refreshSession/"
            ).authenticated()
            .antMatchers(HttpMethod.POST,
                "/api/user/verify",
                "/api/user/verify/"
            ).hasRole("NOT_VERIFIED")
            .antMatchers(
                "/api/user/coverImage",
                "/api/user/coverImage/",
                "/api/user/profileImage",
                "/api/user/profileImage/",
                "/api/user/following/{id:[\\\\d]+}/",
                "/api/user/following/{id:[\\\\d]+}",
                "/api/user/notifications/{id:[\\\\d]+}/",
                "/api/user/notifications/{id:[\\\\d]+}",
                "/api/user/notifications/",
                "/api/user/notifications",
                "/api/user/unseenNotifications/",
                "/api/user/unseenNotifications"
            ).authenticated()
            .antMatchers(
                "/api/user/jobs/",
                "/api/user/jobs",
                "/api/user/jobs/requests/",
                "/api/user/jobs/requests",
                "/api/user/account/provider/",
                "/api/user/account/provider"
            ).hasRole("PROVIDER")
            .antMatchers(
                "/api/jobs/requests/{id:[\\\\d]+}/",
                "/api/jobs/requests/{id:[\\\\d]+}",
                "/api/user/jobs/sentRequests/",
                "/api/user/jobs/sentRequests",
                "/api/user/account/provider/",
                "/api/user/account/provider",
                "/api/user/contactInfo/",
                "/api/user/contactInfo"
            ).hasRole("VERIFIED")

            // --------- /jobs route ---------
            .antMatchers(HttpMethod.POST,
                "/api/jobs",
                "/api/jobs/")
            .hasRole("PROVIDER")
            .antMatchers(HttpMethod.POST,
                "/api/jobs/{id:[\\\\d]+}/contact",
                "/api/jobs/{id:[\\\\d]+}/contact/")
            .hasRole("VERIFIED")
            .antMatchers(HttpMethod.PUT,
                "/api/jobs/{id:[\\\\d]+}",
                "/api/jobs/{id:[\\\\d]+}/")
            .hasRole("PROVIDER")
            .antMatchers(HttpMethod.POST,
                "/api/jobs/{id:[\\\\d]+}/reviews",
                "/api/jobs/{id:[\\\\d]+}/reviews/")
            .hasRole("VERIFIED")

            //else
            .antMatchers("api/**").permitAll()

            .and().csrf().disable()

            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
            .antMatchers("/css/**", "/js/**", "/images/**", "/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
