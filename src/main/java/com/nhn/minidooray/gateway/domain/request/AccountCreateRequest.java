package com.nhn.minidooray.gateway.domain.request;

import com.nhn.minidooray.gateway.domain.Account;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Generated
public class AccountCreateRequest {

    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String email;

    public Account toAccount() {
        return Account.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
