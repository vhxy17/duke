package com.golden.exceptions.parseErrors;

import com.golden.exceptions.ValidationException;

public class MissingArgumentException extends ValidationException {
    public MissingArgumentException(String what) {
        super("Missing: " + what + "!!");
    }
}
