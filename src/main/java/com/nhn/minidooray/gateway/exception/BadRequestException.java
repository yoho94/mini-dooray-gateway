package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WebException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
