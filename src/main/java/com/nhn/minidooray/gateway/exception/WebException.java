package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;

public class WebException extends RuntimeException {

    private final HttpStatus status;

    public WebException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
