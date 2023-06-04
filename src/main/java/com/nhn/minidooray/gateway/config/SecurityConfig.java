package com.nhn.minidooray.gateway.config;

import com.nhn.minidooray.gateway.service.impl.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${com.nhn.minidooray.mapping.project-prefix}")
    private String projectPrefix;

    @Value("${com.nhn.minidooray.mapping.account-prefix}")
    private String accountPrefix;

    private final OAuth2UserServiceImpl oAuth2UserService;
    private final ProjectAccountMappingProperties projectAccountMappingProperties;
    private final TaskMappingProperties taskMappingProperties;
    private final CommentMappingProperties commentMappingProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests(a -> a
                        .antMatchers(accountPrefix + "/**").authenticated()

                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getList()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).LIST, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getRead()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).READ, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getWrite()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).WRITE, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getModify()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getDelete()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).DELETE, 'Account')")

                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getList()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).LIST, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getRead()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).READ, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getWrite()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).WRITE, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getModify()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getDelete()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).DELETE, 'Task')")

                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + commentMappingProperties.getPrefix() + commentMappingProperties.getRead()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).READ, 'Comment')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + commentMappingProperties.getPrefix() + commentMappingProperties.getWrite()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).WRITE, 'Comment')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + commentMappingProperties.getPrefix() + commentMappingProperties.getModify()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY, 'Comment')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + commentMappingProperties.getPrefix() + commentMappingProperties.getDelete()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).DELETE, 'Comment')")

                        .antMatchers("/project/**").authenticated()
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
