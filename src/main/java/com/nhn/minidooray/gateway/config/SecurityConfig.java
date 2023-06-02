package com.nhn.minidooray.gateway.config;

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
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(a -> a
                        .antMatchers("/account/**").authenticated()
                        .antMatchers("/project/**").authenticated()
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
                        .authorizedClientService(authorizedClientService()))
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
            .clientId("c5e2dfd873d560fc151e")
            .clientSecret("a9ae95c45744af282a008086bd8b5fb6918510e6")
            .build();
    }
}
