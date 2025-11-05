package com.golden.parser;

import com.golden.commands.*;
import com.golden.exceptions.BotException;
import com.golden.util.Helper;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
import com.golden.exceptions.parseErrors.*;

public final class LineParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private LineParser(){}

    public static Command parseInput(String line) throws BotException {

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            throw new MissingArgumentException("...Wait a minute!\nI can't read whitespace!" +
                    "\nBut don't worry, I'm still listening...");
        }

        String[] parts = trimmedLine.split("\\s+", 2);
        String command = parts[0].trim().toLowerCase();

        switch (command) {
            case "bye":
            case "goodbye":
                return new ExitCommand();

            case "list":
                return new ListCommand();

            case "mark":
                Helper.requireArgs(parts, 2, "task number");
                return new MarkCommand(parseNumber(parts[1]));
            case "unmark":
                Helper.requireArgs(parts, 2, "task number");
                return new UnmarkCommand(parseNumber(parts[1]));
            case "delete":
                Helper.requireArgs(parts, 2, "task number");
                return new MarkCommand(parseNumber(parts[1]));

            case "todo":
            case "deadline":
            case "event":
                Helper.requireArgs(parts, 2, "task description");
                return new TodoCommand(trimmedLine);

            default:
                throw new UnknownCommandException(command);
        }
    }

    private static int parseNumber(String s) throws IllegalArgumentException {
        String arg = s.trim();
        try {
            int number = Integer.parseInt(arg);
            if (number <= 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(arg);
        }
    }

}