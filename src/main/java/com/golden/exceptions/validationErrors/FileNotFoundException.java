package com.golden.exceptions.validationErrors;

import com.golden.exceptions.ValidationException;

public class FileNotFoundException extends ValidationException {
    public FileNotFoundException(String details) {
        super(details);
    }
}