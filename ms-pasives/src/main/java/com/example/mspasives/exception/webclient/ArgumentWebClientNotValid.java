package com.example.mspasives.exception.webclient;

import com.example.mspasives.util.I18AbleException;

public class ArgumentWebClientNotValid extends I18AbleException {
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}
