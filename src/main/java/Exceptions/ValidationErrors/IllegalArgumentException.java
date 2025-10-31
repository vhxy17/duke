package Exceptions.ValidationErrors;

import Exceptions.ValidationException;

public class IllegalArgumentException extends ValidationException {
    public IllegalArgumentException(String what) {
        super("Illegal Argument- " + what + ".");
    }
}
