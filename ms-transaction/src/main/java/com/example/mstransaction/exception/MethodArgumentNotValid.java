package com.example.mstransaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MethodArgumentNotValid extends ResponseStatusException {
    public MethodArgumentNotValid(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }
}
