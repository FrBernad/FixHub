package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.CustomAccessDeniedHandler;
import ar.edu.itba.paw.webapp.auth.RefererRedirectionAuthenticationSuccessHandler;
import ar.edu.itba.paw.webapp.auth.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;

    @Value("classpath:auth/auth_key.pem")
    private Resource authKey;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RefererRedirectionAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .and().authorizeRequests()
            //session routes
            .antMatchers("/login", "/register").anonymous()
            .antMatchers(HttpMethod.POST, "/user/verifyAccount/resend").hasRole("NOT_VERIFIED")
            .antMatchers("/user/verifyAccount/resendConfirmation").hasRole("NOT_VERIFIED")
            .antMatchers("/user/verifyAccount").hasRole("USER")
            .antMatchers("/logout").authenticated()

            //profile routes
            .antMatchers("/user/account").hasRole("USER")
            .antMatchers("/user/account/search", "/user/account/update",
                "/user/account/updateCoverImage", "/user/account/updateInfo",
                "/user/account/updateProfileImage","/user/account/edit").hasRole("VERIFIED")
            .antMatchers(HttpMethod.POST,"/user/follow","/user/unfollow").hasRole("VERIFIED")

            //jobs routes
            .antMatchers("/jobs/{id:[\\d]+}/contact").hasRole("VERIFIED")
            .antMatchers("/jobs/new").hasRole("PROVIDER")
            .antMatchers(HttpMethod.POST, "/jobs/{id:[\\d]+}").hasRole("VERIFIED")
            .antMatchers( "/jobs/{id:[\\d]+}/edit").hasRole("PROVIDER")

            //provider routes
            .antMatchers("/user/dashboard", "/user/dashboard/search").hasRole("PROVIDER")
            .antMatchers("/user/join", "/user/join/chooseCity").hasRole("VERIFIED")

            //else
            .antMatchers("/**").permitAll()

            .and().formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/user/account", false)
            .failureUrl("/login?error=true")

            .and().rememberMe()
            .rememberMeParameter("rememberMe")
            .userDetailsService(userDetailService)
            .key(FileCopyUtils.copyToString(new InputStreamReader(authKey.getInputStream())))
            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))

            .and().logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")

            .and().exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler())
            .and().csrf().disable();
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
