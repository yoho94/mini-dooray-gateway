package com.nhn.minidooray.gateway.config;

import com.nhn.minidooray.gateway.service.impl.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${com.nhn.minidooray.gateway.config.github.client-id}")
    private String githubClientId;
    @Value("${com.nhn.minidooray.gateway.config.github.client-secret}")
    private String githubClientSecret;

    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(a -> a
                        .antMatchers("/account/**").authenticated()
                        .antMatchers("/project/**").access("@projectAuthChecker.check(httpServletRequest, authentication)")
                        .antMatchers("/redirect-index").authenticated()
                        .anyRequest().permitAll())
                .requiresChannel(r -> r
                        .antMatchers("/account/**").requiresSecure()
                        .antMatchers("/project/**").requiresSecure()
                        .anyRequest().requiresInsecure())
                .headers(h -> h
                        .defaultsDisabled()
                        .frameOptions().sameOrigin()
                        .xssProtection(x -> x.block(true)))
                .exceptionHandling(e -> e
                        .accessDeniedPage("/error/403"))
                .formLogin().and()
                .oauth2Login(o -> o
                        .clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientService(authorizedClientService())
                        .userInfoEndpoint(u -> u
                                .userService(oAuth2UserService)))
                .logout(l -> l
                        .invalidateHttpSession(true))
                .csrf().disable()
                .sessionManagement(s -> s.sessionConcurrency(sc -> sc
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false))
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))
//                .authenticationProvider(residentAuthenticationProvider(null, null))
//                .authenticationProvider(memoryAuthenticationProvider(null))
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(github());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .userNameAttributeName("name")
                .clientId(githubClientId)
                .clientSecret(githubClientSecret)
                .scope("read:user", "user:email")
                .build();
    }
}
