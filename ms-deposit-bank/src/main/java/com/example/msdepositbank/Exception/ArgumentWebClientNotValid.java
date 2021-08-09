package com.example.msdepositbank.Exception;

import com.example.msdepositbank.util.I18AbleException;

public class ArgumentWebClientNotValid extends I18AbleException {
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}
