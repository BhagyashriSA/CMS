package com.in.cafe.jwt;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter, AuthenticationEntryPoint point) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll() // Allow login without authentication
                .requestMatchers("/user/**").authenticated() // Require authentication for /home/**
                .anyRequest().authenticated() // Secure all other endpoints
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Custom authentication entry point
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session policy to stateless
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before authentication filter

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	http.csrf(csrf -> csrf.disable())
//        .cors(cors -> cors.disable())
//        .authorizeHttpRequests( auth -> 
//        auth.requestMatchers("/home/**").authenticated().requestMatchers("/auth/login").permitAll()
//        .anyRequest()
//        .authenticated()
//        .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//  }
	
    

}
