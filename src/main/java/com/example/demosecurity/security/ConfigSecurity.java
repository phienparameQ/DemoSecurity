package com.example.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigSecurity {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService(BCryptPasswordEncoder pe) throws Exception{
        UserDetails user1 = User.withUsername("user1")
                .password(pe.encode("123"))
                .roles("GUEST")
                .build();

        UserDetails user2 = User.withUsername("user2")
                .password(pe.encode("123"))
                .roles("USER","GUEST")
                .build();

        UserDetails user3 = User.withUsername("user3")
                .password(pe.encode("123"))
                .roles("ADMIN","USER","GUEST")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    //Phân quyền sử dụng
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((request) -> request
                .anyRequest().permitAll());



        http.formLogin((login) -> login
                        .loginPage("/auth/login/form")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/auth/login/success",false)
                        .failureUrl("/auth/login/error"));
//                        .usernameParameter("username")
//                        .passwordParameter("password")

        http.exceptionHandling((error)-> error
                        .accessDeniedPage("/auth/access/denied"));

        http.logout((logout) -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/logout/success")
                .addLogoutHandler(new SecurityContextLogoutHandler()));

        return http.build();
    }

}
