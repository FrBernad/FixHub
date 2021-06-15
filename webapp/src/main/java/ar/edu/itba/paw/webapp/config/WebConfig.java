package ar.edu.itba.paw.webapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableTransactionManagement
@EnableAsync
@EnableWebMvc
@ComponentScan({"ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence",})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    private static final int MAX_SIZE_PER_FILE = 3000000;

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();

        ds.setDriverClass(org.postgresql.Driver.class);
//        ds.setUrl("jdbc:postgresql://localhost/paw");
//        ds.setUsername("root");
//        ds.setPassword("root");
        ds.setUrl("jdbc:postgresql://10.16.1.110/paw-2021a-06");
        ds.setUsername("paw-2021a-06");
        ds.setPassword("QroE40tsz");
        return ds;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(MAX_SIZE_PER_FILE);
        multipartResolver.setMaxUploadSize(MAX_SIZE_PER_FILE * 6);
        return multipartResolver;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
//        properties.setProperty("hibernate.show_sql", "true");// FIXME:Si ponen esto en prod, hay tabla!!! SACAR EN DEPLOY!!!
//        properties.setProperty("format_sql", "true");

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }

    @Bean(name = "appHost")
    public String appHost() {
        return "pawserver.it.itba.edu.ar";
    }

    @Bean(name = "appScheme")
    public String appScheme() {
        return "http";
    }

    @Bean(name = "appRootPath")
    public String appRootPath() {
        return "/paw-2021a-06/";
    }


    @Bean(name = "appPort")
    public int appPort() {
        return 80;
    }


}
