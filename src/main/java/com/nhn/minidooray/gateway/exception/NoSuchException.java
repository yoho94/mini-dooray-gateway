package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;

public class NoSuchException extends WebException {
    public NoSuchException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
