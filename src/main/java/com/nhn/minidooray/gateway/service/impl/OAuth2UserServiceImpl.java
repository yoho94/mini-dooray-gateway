package com.nhn.minidooray.gateway.service.impl;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.enums.AccountStateType;
import com.nhn.minidooray.gateway.exception.WebException;
import com.nhn.minidooray.gateway.service.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User>, MessageSourceAware {

    private final AccountApiService accountApiService;

    @Value("${com.nhn.minidooray.gateway.dormant_days}")
    private int dormantDays;

    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException("email is null or blank, 깃허브 이메일을 공개해야 이용 가능합니다.");
        }

        String name = oAuth2User.getAttribute("name");

        if (name == null || name.isBlank()) {
            throw new OAuth2AuthenticationException("name is null or blank, 깃허브 이름을 설정 및 공개해야 이용 가능합니다.");
        }

        Integer id = oAuth2User.getAttribute("id");
        String loginId = oAuth2User.getAttribute("login");

        Account account = null;
        try {
            account = accountApiService.getAccountByEmail(email);

            AccountStateType accountStateType = AccountStateType.valueOfCode(account.getAccountStateCode());
            LocalDateTime lastLoginAt = account.getLastLoginAt();

            if (accountStateType.isDisabled()) {
                throw new DisabledException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.disabled"));
            }

            if (lastLoginAt != null) {

                LocalDateTime accountStateChangeAt = account.getAccountStateChangeAt();

                long loginDays = Duration.between(lastLoginAt, LocalDateTime.now()).toDays();
                long changeDays = Duration.between(accountStateChangeAt, LocalDateTime.now()).toDays();

                boolean accountLocked = accountStateType.isAccountLocked();

                // 계정 잠금(휴면)이 아니라면 아래 휴면 계정 검사 로직 start
                if (!accountLocked && (dormantDays <= loginDays)) {
                    if (accountStateType == AccountStateType.ACTIVE) {
                        if (changeDays >= dormantDays) {
                            accountApiService.addAccountState(account.getId(), AccountStateType.DORMANT);
                            accountLocked = true;
                        }
                    } else {
                        accountApiService.addAccountState(account.getId(), AccountStateType.DORMANT);
                        accountLocked = true;
                    }
                }

                if (accountLocked) {
                    throw new LockedException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.locked"));
                }
            }

        } catch (WebException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                account = Account.builder()
                        .id(loginId + "@" + id)
                        .name(name)
                        .email(email)
                        .build();

                accountApiService.addAccount(account);
            } else {
                throw e;
            }
        }

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("accountId", account.getId());

        return new DefaultOAuth2User(
                Collections.emptySet(),
                attributes,
                "accountId"
        );
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }
}
