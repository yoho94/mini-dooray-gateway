package com.nhn.minidooray.gateway.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Generated
public class Account {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;
    private String accountStateCode;
    private LocalDateTime accountStateChangeAt;
}
