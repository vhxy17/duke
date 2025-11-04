package com.golden.parser;

//import Exceptions.BotException;
//import Exceptions.ParseErrors.UnknownCommandException;
//import Exceptions.ValidationErrors.IndexOutOfBoundsException;
//import Exceptions.ValidationErrors.MissingArgumentException;
//import Exceptions.ValidationErrors.IllegalArgumentException;
//import Exceptions.ValidationErrors.*;

import com.golden.core.BotActions;
import com.golden.exceptions.*;
import com.golden.exceptions.parseErrors.UnknownCommandException;
import com.golden.exceptions.validationErrors.MissingArgumentException;
import com.golden.util.Helper;

public final class LineParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private LineParser(){}

//    public static boolean hasMissingArgs(String[] parts, int targetArgCount){
//        return !(parts.length == targetArgCount);
//    }

    public static boolean parseInput(String line, BotActions actions) throws MissingArgumentException,
            UnknownCommandException, IllegalArgumentException, IndexOutOfBoundsException {
        if (line == null) return true;

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            throw new MissingArgumentException("...Wait a minute!\nI can't read whitespace!" +
                    "\nBut don't worry, I'm still listening...");
        }

        String[] parts = trimmedLine.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        switch (command) {
            case "bye":
                actions.sayBye();
                return false;

            case "list":
                // to update to load from file
                actions.printList();
                return true;

            case "mark":
                if (Helper.hasMissingArgs(parts, 2)){
                    throw new MissingArgumentException("task number");
                }
                try {
                    int listNumber = Integer.parseInt(parts[1].trim());
                    actions.mark(listNumber);
                    return true;
                } catch (NumberFormatException e) {
                    String arg = parts[1].trim();
                    throw new IllegalArgumentException(arg);
                }

            case "unmark":
                if (Helper.hasMissingArgs(parts, 2)){
                    throw new MissingArgumentException("task number");
                }
                try {
                    int listNumber = Integer.parseInt(parts[1].trim());
                    actions.unmark(listNumber);
                    return true;
                } catch (NumberFormatException e) {
                    String arg = parts[1].trim();
                    throw new IllegalArgumentException(arg);
                }

            case "todo":
            case "deadline":
            case "event":
                if (Helper.hasMissingArgs(parts, 2)){
                    throw new MissingArgumentException("task description");
                }
                try {
                    actions.addToList(trimmedLine);
                } catch (BotException e) {
                    Helper.printFormattedReply(e.toString());
                }

                return true;

            case "delete":
                if (Helper.hasMissingArgs(parts, 2)){
                    throw new MissingArgumentException("task description");
                }
                try {
                    int listNumber = Integer.parseInt(parts[1].trim());
                    actions.delete(listNumber);
                    return true;
                } catch (NumberFormatException e) {
                    String arg = parts[1].trim();
                    throw new IllegalArgumentException(arg);
                }
            default:
//                actions.echo(trimmedLine);
//                return true;
                throw new UnknownCommandException(command);
        }
    }

}