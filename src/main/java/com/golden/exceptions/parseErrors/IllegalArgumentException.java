package com.golden.exceptions.parseErrors;

import com.golden.exceptions.ValidationException;

public class IllegalArgumentException extends ValidationException {
    public IllegalArgumentException(String what) {
        super("Illegal Argument: " + what + ".");
    }
}
