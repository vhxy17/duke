package Exceptions.ValidationErrors;

import Exceptions.ValidationException;

public class IndexOutOfBoundsException extends ValidationException {
    public IndexOutOfBoundsException(int index) {
        super("Index [" + index + "] is out of range.");
    }
}