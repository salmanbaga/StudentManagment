package com.Project.Student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 👤 USER access
                        .requestMatchers("/student/get").hasAnyRole("USER", "ADMIN")

                        // 👑 ADMIN only
                        .requestMatchers("/student/delete/**").hasRole("ADMIN")

                        // 👑 ADMIN only
                        .requestMatchers("/student/put/**").hasRole("ADMIN")

                        // 👑 ADMIN only
                        .requestMatchers("/student/post/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
