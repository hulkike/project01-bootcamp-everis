package com.example.msacquisitionbank.exception;

import com.example.msacquisitionbank.utils.I18AbleException;

public class ArgumentWebClientNotValid extends I18AbleException {
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}