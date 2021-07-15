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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

    //    FIXME: volver a habilitar security
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and().authorizeRequests()
//            //session routes
//            .antMatchers("/login", "/register").anonymous()
            .antMatchers("/login", "/register").anonymous()

//            .antMatchers(HttpMethod.POST, "/user/verifyAccount/resend").hasRole("NOT_VERIFIED")
//            .antMatchers("/user/verifyAccount/resendConfirmation").hasRole("NOT_VERIFIED")
//            .antMatchers("/user/verifyAccount").hasRole("USER")
//            .antMatchers("/logout").authenticated()

            .antMatchers(HttpMethod.GET, "/api/user").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/user").authenticated()
//            //profile routes
//            .antMatchers("/user/account").hasRole("USER")
//            .antMatchers("/user/account/search", "/user/account/update",
//                "/user/account/updateCoverImage", "/user/account/updateInfo",
//                "/user/account/updateProfileImage", "/user/account/edit").hasRole("VERIFIED")
//            .antMatchers(HttpMethod.POST, "/user/follow", "/user/unfollow").hasRole("VERIFIED")
//
//            //jobs routes
            .antMatchers(HttpMethod.POST, "/api/jobs/{id:[\\\\d]+}/contact").hasRole("VERIFIED")
            .antMatchers(HttpMethod.POST, "/api/jobs/").hasRole("PROVIDER")
            .antMatchers(HttpMethod.PUT, "/api/jobs/{id:[\\\\d]+}").hasRole("PROVIDER")
            .antMatchers(HttpMethod.POST,"/api/jobs/{id:[\\\\d]+}/reviews").hasRole("VERIFIED")
//            .antMatchers(HttpMethod.POST, "/jobs/{id:[\\d]+}").hasRole("VERIFIED")
//            .antMatchers("/jobs/{id:[\\d]+}/edit").hasRole("PROVIDER")
//
//            //provider routes
//            .antMatchers("/user/dashboard", "/user/dashboard/search",
//                "/user/dashboard/contacts/acceptJob",
//                "/user/dashboard/contacts/rejectJob",
//                "/user/dashboard/contacts/completedJob").hasRole("PROVIDER")
//            .antMatchers("/user/join", "/user/join/chooseCity").hasRole("VERIFIED")

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
