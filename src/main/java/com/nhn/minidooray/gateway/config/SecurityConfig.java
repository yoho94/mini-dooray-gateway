package com.nhn.minidooray.gateway.config;

import com.nhn.minidooray.gateway.handler.LoginSuccessHandler;
import com.nhn.minidooray.gateway.service.impl.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserServiceImpl oAuth2UserService;
    private final ProjectAccountMappingProperties projectAccountMappingProperties;
    private final TaskMappingProperties taskMappingProperties;
    private final CommentMappingProperties commentMappingProperties;
    private final ProjectMappingProperties projectMappingProperties;
    private final AccountMappingProperties accountMappingProperties;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final String projectPrefix = projectMappingProperties.getPrefix();
        final String accountPrefix = accountMappingProperties.getPrefix();

        return http.authorizeRequests(a -> a
                        .antMatchers(accountPrefix + accountMappingProperties.getWrite()).permitAll()
                        .antMatchers(accountPrefix + "/**").authenticated()
                        // TODO ACCOUNT의 권한에 따라 PROJECT 관리 가능하도록 DB 수정 및 로직 수정 필요
                        .antMatchers(projectPrefix + projectMappingProperties.getModify()).access("@projectAuthChecker.projectAuthCheck(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY)")
                        .antMatchers(projectPrefix + projectMappingProperties.getModifyState()).access("@projectAuthChecker.projectAuthCheck(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY)")
                        .antMatchers(projectPrefix + projectMappingProperties.getDelete()).access("@projectAuthChecker.projectAuthCheck(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).DELETE)")

                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getList()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).LIST, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getRead()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).READ, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getWrite()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).WRITE, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getModify()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).MODIFY, 'Account')")
                        .antMatchers(projectPrefix + "/*" + projectAccountMappingProperties.getPrefix() + projectAccountMappingProperties.getDelete()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).DELETE, 'Account')")

                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + taskMappingProperties.getList()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).LIST, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + "/*" + taskMappingProperties.getRead()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).READ, 'Task')")
                        .antMatchers(projectPrefix + "/*" + taskMappingProperties.getPrefix() + taskMappingProperties.getWrite()).access("@projectAuthChecker.check(request, authentication, T(com.nhn.minidooray.gateway.domain.enums.ProjectAuthorityType.PermissionType).WRITE, 'Task')")
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
                .formLogin(h -> h
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler))
                .oauth2Login(o -> o
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
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
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        authenticationProvider.setMessageSource(messageSource());

        return authenticationProvider;
    }

    // https://shinsunyoung.tistory.com/79
    // 위 링크 참고해서 작성.
    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8"); // 인코딩 설정
        messageSource.setBasenames("classpath:message/view_message", "classpath:message/security_message", "classpath:org/springframework/security/messages"); // 커스텀한 properties 파일, security properties 파일 순서대로 설정
        return messageSource;
    }

}
