package com.josephcsoftware.tsgstage2.configs;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration.ProviderDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        OidcUserService googleUserService = new OidcUserService();

        // Set evals for what should be retrieved
        googleUserService.setRetrieveUserInfo(this::shouldRetrieveUserInfo);

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
            .oauth2Login(oauthLogin -> oauthLogin.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                                                   .oidcUserService(googleUserService)
                                                                   )
                         );
        return http.build();
    }

    private boolean shouldRetrieveUserInfo(OidcUserRequest userRequest) {
        // Get the scope we're interested in
        final Set<String> scopes = Set.of(
                                    OidcScopes.PROFILE,
                                    OidcScopes.EMAIL,
                                    OidcScopes.OPENID
                                    );
        ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();

        // Skip, if the provider doesn't have a user info endpoint
        if (!StringUtils.hasLength(providerDetails.getUserInfoEndpoint().getUri())) {
            return false;
        }

        // Verify that we are using an auth code for this process
        if (AuthorizationGrantType.AUTHORIZATION_CODE
            .equals(userRequest.getClientRegistration().getAuthorizationGrantType())) {
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            // Does the token have what we're looking for?
            return CollectionUtils.isEmpty(accessToken.getScopes())
                || CollectionUtils.containsAny(accessToken.getScopes(), scopes);
        }
        return false;
    }
}
