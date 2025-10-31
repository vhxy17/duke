package Exceptions.ValidationErrors;

import Exceptions.ValidationException;

public class MissingArgumentException extends ValidationException {
    public MissingArgumentException(String what) {
        super("Missing " + what + "!!");
    }
}
