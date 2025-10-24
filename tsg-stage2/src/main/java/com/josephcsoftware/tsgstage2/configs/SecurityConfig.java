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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String jwkSetUri;

    @Value("${jgc.project.root}/login")
    private String publicSpace;

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
                                   .requestMatchers(this.publicSpace).permitAll()
                                   // Anything else should be locked down
                                   .anyRequest().authenticated()
                                   )
            .oauth2ResourceServer(oauth2 -> oauth2
                                  // Pass the JWT to Nimbus for decoding
                                  .jwt(withDefaults())
                                  );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4173", "http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(43200L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Builds a Nimbus JWT decoder, when defaults are requested
    @Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
	}
}
