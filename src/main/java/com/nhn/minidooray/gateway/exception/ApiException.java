package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends WebException {
    public ApiException() {
        super("API ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
