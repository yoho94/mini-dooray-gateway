package com.nhn.minidooray.gateway.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Generated
public class AccountStateCreateRequest {

    private String accountId;
    private String accountStateCode;

}
