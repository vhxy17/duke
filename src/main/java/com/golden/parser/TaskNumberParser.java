package com.golden.parser;

import com.golden.exceptions.BotException;
import com.golden.exceptions.ParseException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
import com.golden.util.ParseHelper;

public class TaskNumberParser {
    private TaskNumberParser(){}

    public static int parseNumber(String s) throws BotException {
        String arg = s.trim();
        String[] markArgParts = ParseHelper.splitOnWhitespaces(arg);
        ParseHelper.requireArgs(markArgParts, 1, "task number");

        try {
            int number = Integer.parseInt(arg);
            if (number <= 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("%s. (Expected: valid task number).", arg));
        }
    }
}
//String.format("%d . Number out of range", number)