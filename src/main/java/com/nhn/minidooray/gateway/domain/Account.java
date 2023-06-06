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
    /*
      "id": "exampleID",
      "password": "securePassword",
      "name": "Example Name",
      "email": "example@email.com",
      "lastLoginAt": null,
      "createdAt": "2023-06-05T14:28:08",
      "accountAccountStateCode": "03",
      "accountAccountStateChangeAt": "2023-06-05T16:25:29"
     */
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;
    private String accountStateCode;
    private LocalDateTime accountStateChangeAt;
}
