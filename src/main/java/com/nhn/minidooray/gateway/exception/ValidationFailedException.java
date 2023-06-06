package com.nhn.minidooray.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends WebException {

    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
            .stream()
            .map(error -> new StringBuilder().append("ObjectName=").append(error.getObjectName())
                .append(",Message=").append(error.getDefaultMessage())
                .append(",code=").append(error.getCode()))
            .collect(Collectors.joining(" | ")), HttpStatus.BAD_REQUEST);
    }
}
