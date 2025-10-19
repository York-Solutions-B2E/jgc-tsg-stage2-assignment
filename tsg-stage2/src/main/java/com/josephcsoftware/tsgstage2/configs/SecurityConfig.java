package com.josephcsoftware.tsgstage2.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS with default settings
            .cors(Customizer.withDefaults())
            // Disable cross-site request forgery checks;
            // this is a non-browser endpoint, so this will simplify things
            .csrf(csrf -> csrf.disable())
            // Continue with the permissions/auth chain
            .authorizeHttpRequests(auth -> auth
                                   // We should be able to freely log in
                                   .requestMatchers("/api/auth/**").permitAll()
                                   // Anything else should be locked down
                                   .anyRequest().authenticated()
                                   )
            .oauth2ResourceServer(oauth2 -> oauth2
                                  // Pass the JWT to Nimbus for decoding
                                  .jwt(withDefaults())
                                  );

        return http.build();
    }

    // Builds a Nimbus JWT decoder, when defaults are requested
    @Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
	}
}
