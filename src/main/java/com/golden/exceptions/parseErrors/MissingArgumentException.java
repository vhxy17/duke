package com.golden.exceptions.parseErrors;

import com.golden.exceptions.ParseException;

public class MissingArgumentException extends ParseException {
    public MissingArgumentException(String what) {
        super(what);
    }
}
