package com.example.mstransaction.exception.webclient;

import com.example.mstransaction.utils.I18AbleException;

public class ArgumentWebClientNotValid extends I18AbleException {
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}
