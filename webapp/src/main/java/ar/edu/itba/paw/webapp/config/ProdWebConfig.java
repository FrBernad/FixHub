package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!dev")
@Configuration
public class ProdWebConfig {

    @Bean(name = "appHost")
    public String appHost() {
        return "pawserver.it.itba.edu.ar";
    }

    @Bean(name = "appProtocol")
    public String appProtocol() {
        return "http";
    }

    @Bean(name = "appWebContext")
    public String appWebContext() {
        return "/paw-2021a-06/";
    }

    @Bean(name = "appPort")
    public int appPort() {
        return 80;
    }

}
