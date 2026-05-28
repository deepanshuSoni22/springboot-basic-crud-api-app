package com.example.booksmanagementapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.GET,
                                "/books",
                                "/books/book/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) ->
                        response.sendError(401, "Unauthorized!")) // no redirect, just send http code
                    .accessDeniedHandler((request, response, accessDeniedException) ->
                        response.sendError(403, "Forbidden"))
                );

        return http.build();

    }

}
