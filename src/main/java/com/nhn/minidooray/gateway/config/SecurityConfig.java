package com.nhn.minidooray.gateway.config;

import com.nhn.minidooray.gateway.service.impl.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(a -> a
                        .antMatchers("/account/**").authenticated()
                        .antMatchers("/project/**").access("@projectAuthChecker.check(request, authentication)")
                        .antMatchers("/redirect-index").authenticated()
                        .anyRequest().permitAll())
                .requiresChannel(r -> r
                        .anyRequest().requiresSecure())
                .headers(h -> h
                        .defaultsDisabled()
                        .frameOptions().sameOrigin()
                        .xssProtection(x -> x.block(true)))
                .exceptionHandling(e -> e
                        .accessDeniedPage("/error/403"))
                .formLogin().and()
                .oauth2Login(o -> o
                        .userInfoEndpoint(u -> u
                                .userService(oAuth2UserService)))
                .logout(l -> l
                        .invalidateHttpSession(true))
                .csrf().disable()
                .sessionManagement(s -> s.sessionConcurrency(sc -> sc
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false))
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

}
