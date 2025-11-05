package com.golden.exceptions;

public enum ErrorCode {
    GENERIC("Oops! Something went wrong."),
    VALIDATION_ERROR("Oops! There is a problem with your input."),
    PARSE_ERROR("Sorry, I do not understand this command"),
    STORAGE_ERROR("Oops! File error");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
    public String message(){
        return message;
    }
}