package Exceptions.ValidationErrors;

import Exceptions.ValidationException;

public class FileNotFoundException extends ValidationException {
    public FileNotFoundException(String details) {
        super(details);
    }
}