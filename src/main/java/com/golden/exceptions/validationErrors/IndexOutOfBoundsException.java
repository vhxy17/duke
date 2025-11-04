package com.golden.exceptions.validationErrors;

import com.golden.exceptions.ValidationException;

public class IndexOutOfBoundsException extends ValidationException {
    public IndexOutOfBoundsException(int index) {
        super("Index [" + index + "] is out of range.");
    }
}