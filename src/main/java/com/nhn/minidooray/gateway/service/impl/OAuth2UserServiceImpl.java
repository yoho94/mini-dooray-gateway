package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.service.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountApiService accountApiService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is null or blank, 깃허브 이메일을 공개해야 이용 가능합니다.");
        }

        String name = oAuth2User.getAttribute("name");

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is null or name, 깃허브 이름을 설정 및 공개해야 이용 가능합니다.");
        }

        Integer id = oAuth2User.getAttribute("id");
        String loginId = oAuth2User.getAttribute("login");

        Account account = accountApiService.getAccountByEmail(email);

        if (account == null) {
            account = accountApiService.addAccount(
                    Account.builder()
                            .id(loginId + "@" + id)
                            .name(name)
                            .email(email)
                            .salt("") // TODO
                            .password("") // TODO
                            .build()
            );
        }

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("accountId", account.getId());

        return new DefaultOAuth2User(
                Collections.emptySet(),
                attributes,
                "accountId"
        );
    }
}
