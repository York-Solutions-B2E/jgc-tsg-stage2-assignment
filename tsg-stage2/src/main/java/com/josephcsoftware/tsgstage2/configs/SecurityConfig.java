package com.josephcsoftware.tsgstage2.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                                   // Public does not require auth
                                   .requestMatchers("/api/public/**").permitAll()
                                   // Login does not require auth
                                   .requestMatchers("/login").permitAll()
                                   // The rest does
                                   .anyRequest().authenticated()
                                   );
        return http.build();
    }
}
