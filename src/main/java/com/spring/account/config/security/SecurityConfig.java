package com.spring.account.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * {@link org.springframework.security.config.annotation.web.WebSecurityConfigurer} implementation, which configures
 * {@link org.springframework.security.core.userdetails.UserDetailsService}, {@link org.springframework.security.web.AuthenticationEntryPoint} and
 * {@link org.springframework.security.web.access.AccessDeniedHandler} implementations to use. Contains the
 * {@link org.springframework.security.crypto.password.PasswordEncoder} bean method to use throughout the app.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsService       userDetailsService;
    AuthenticationEntryPoint authenticationEntryPoint;
    AccessDeniedHandler      accessDeniedHandler;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .csrf().disable().headers().frameOptions().disable();

        http.exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler);

    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(13);
    }

}