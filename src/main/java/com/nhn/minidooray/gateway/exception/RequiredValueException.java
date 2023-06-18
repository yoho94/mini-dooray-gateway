package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;

public class RequiredValueException extends WebException {

    public RequiredValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
