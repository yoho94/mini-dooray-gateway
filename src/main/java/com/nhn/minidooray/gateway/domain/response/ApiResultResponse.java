package com.nhn.minidooray.gateway.domain.response;

import com.nhn.minidooray.gateway.exception.NoSuchException;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Generated
public class ApiResultResponse<T> {

    private Header header;
    private List<T> result;

    public long getTotalCount() {
        return result == null ? 0 : result.size();
    }

    public boolean isEmpty() {
        return result == null || result.isEmpty();
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchException("Not Found Result");
        }

        return result.get(0);
    }

    @Getter
    @Setter
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }
}
