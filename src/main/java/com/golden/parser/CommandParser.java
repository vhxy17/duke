package com.golden.parser;

import com.golden.commands.*;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.*;
import com.golden.util.*;

import com.golden.exceptions.parseErrors.IllegalArgumentException;

public final class CommandParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private CommandParser(){}

    public static Command parseCommand(String line) throws BotException {

        final String trimmedLine = ValidationHelper.isNonBlank(line,
                "...Wait a minute!\nI can't read whitespace! But don't worry, I'm still here...");
        final String[] parts = trimmedLine.split("\\s+", 2);
        final String command = parts[0].trim().toLowerCase();

        switch (command) {
            case "bye":
            case "goodbye":
                // check no arguments is passed in after command; suggest corrective actions.
                ParseHelper.requireArgs(parts, 1, "\nTry 'bye' or 'goodbye'.");
                return new ExitCommand();

            case "list":
                // check no arguments is passed in after command; suggest corrective actions.
                ParseHelper.requireArgs(parts, 1, "\nTry 'list'.");
                return new ListCommand();

            case "mark":
                try{
                    return new MarkCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new com.golden.exceptions.parseErrors.IllegalArgumentException("task number not found.");
                }
            case "unmark":
                try{
                    return new UnmarkCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new IllegalArgumentException("task number not found.");
                }
            case "delete":
                try{
                    return new DeleteCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new IllegalArgumentException("task number not found.");
                }

            case "todo":
                // guard against case where only 'todo' is passed without any task description.
                ParseHelper.requireArgs(parts, 2, "task number");
                return new TodoCommand(parts[1].trim());    //handle if parts[1] is null before reaching here
            case "deadline":
                String[] deadlineArgs = TaskParser.parseDeadlineCommand(parts[1].trim());
                return new DeadlineCommand(deadlineArgs);
            case "event":
                String[] eventArgs = TaskParser.parseEventCommand(parts[1].trim());
                return new EventCommand(eventArgs);

            default:
                throw new UnknownCommandException(command);
        }
    }
}

/*
* unhandled exceptions:
* 1. checks for specific keyword- e.g. /by, /from /to                   //done
* 2. check for empty strings after /by, /from, /to                      //done
* 3. catch ArrayIndexOutOfBoundsException from todo/deadline/event      //done
* */