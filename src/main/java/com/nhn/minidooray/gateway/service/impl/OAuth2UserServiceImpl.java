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
import java.util.Map;

// TODO 깃헙 로그인 후) 이메일 조회 후 이메일 있으면 해당 아이디로 로그인, 이메일 없으면 회원가입 시키기
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountApiService accountApiService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String id = userRequest.getClientRegistration().getRegistrationId();
        String userName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email = oAuth2User.getAttribute("email");

        // https://vesselsdiary.tistory.com/192
        // TODO 동작 안하면 위 링크 참고하여 변경 할 것
        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException("email is null or blank");
        }

        Account account = accountApiService.getAccountByEmail(email);

        if (account == null) {
            account = accountApiService.addAccount(
                    Account.builder()
                            .id(id)
                            .name(userName)
                            .email(email)
                            .salt("") // TODO
                            .password("") // TODO
                            .build()
            );
        }

        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.put("id", account.getId());

        return new DefaultOAuth2User(
                Collections.emptySet(),
                attributes,
                "id"
        );
    }
}
