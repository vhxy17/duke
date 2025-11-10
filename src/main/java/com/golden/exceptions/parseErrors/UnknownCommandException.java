package com.golden.exceptions.parseErrors;


import com.golden.exceptions.ParseException;

public class UnknownCommandException extends ParseException {
    public UnknownCommandException(String cmd){
        super("Unknown command: " + cmd + ".");
    }

}
