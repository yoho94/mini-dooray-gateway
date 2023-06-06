package com.nhn.minidooray.gateway.domain.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResultResponse<T> {

    private Header header;
    private List<T> result;

    public long getTotalCount() {
        return result == null ? 0 : result.size();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }
}
