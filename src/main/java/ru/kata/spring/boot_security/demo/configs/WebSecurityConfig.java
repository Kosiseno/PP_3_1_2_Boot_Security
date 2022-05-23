package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;


    private final UserService userService;

    private final PasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("USER","ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .and().formLogin()
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout().permitAll();

    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    //Долго мучался с ошибкой зацикливания контекста, в итоге решилась добавлением такого бина
    @Bean
    public UserDetailsService userDetailsService() {
        return userService::loadUserByUsername;
    }

}