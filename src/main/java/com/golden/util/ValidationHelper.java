package com.golden.util;

import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
import com.golden.exceptions.parseErrors.MissingArgumentException;

public class ValidationHelper {
    // override default public constructor method and makes it private; prevents instantiation by other classes
    private ValidationHelper(){}

    public static boolean isNumberInRange(int lowerLimit, int upperLimit, int number )
            throws IllegalArgumentException {
        if (number < lowerLimit || number > upperLimit){
            throw new IllegalArgumentException(String.format("%d. Number is out of range", number));
        }
        return true;
    }
    /** Ensures line is non-null and not blank; returns trimmed line. */
    public static String isNonBlank(String s, String message) throws MissingArgumentException {
        if (s == null || s.trim().isEmpty()) {
            throw new MissingArgumentException(message);
        }
        return s.trim();
    }

}
