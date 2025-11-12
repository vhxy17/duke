package com.golden.parser;

import com.golden.commands.*;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.*;
import com.golden.util.*;

public final class CommandParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private CommandParser(){}

    /**
     *  Returns the Command object that is detected at the head of the raw string.
     *
     *  @param line The raw string of the full command.
     *
     *  @return     the Command object created from parsing the first word of the line,
     *              or a {@code Throwable} Exception.
     */
    public static Command parseCommand(String line) throws BotException {

        final String trimmedLine = ParseHelper.isNonBlank(line,
                "...Wait a minute!\nI can't read whitespace! But don't worry, I'm still here...");
        final String[] parts = trimmedLine.split("\\s+", 2);
        final String command = parts[0].trim().toLowerCase();

        switch (command) {
            case "hello":
            case "hi":
                return new EchoCommand(command);

            case "bye":
            case "goodbye":
                // check no arguments is passed in after command; suggest corrective actions.
                ParseHelper.requireArgs(parts, 1, "Try 'bye' or 'goodbye'.");
                return new ExitCommand();

            case "list":
                // check no arguments is passed in after command; suggest corrective actions.
                ParseHelper.requireArgs(parts, 1, "Try 'list'.");
                return new ListCommand();

            case "mark":
                try{
                    return new MarkCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new MissingArgumentException("task number is missing!");
                }
            case "unmark":
                try{
                    return new UnmarkCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new MissingArgumentException("task number is missing!");
                }
            case "delete":
                try{
                    return new DeleteCommand(TaskNumberParser.parseNumber(parts[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e){
                    throw new MissingArgumentException("task number is missing!");
                }

            case "todo":
                // guard against case where only 'todo' is passed without any task description.
                ParseHelper.requireArgs(parts, 2, "task description");
                // handle if parts[1] is null in parseTodoCommand
                String[] todoArgs = TaskParser.parseTodoCommand(parts[1].trim());
                return new TodoCommand(todoArgs);
            case "deadline":
                ParseHelper.requireArgs(parts, 2, "task description");
                String[] deadlineArgs = TaskParser.parseDeadlineCommand(parts[1].trim());
                return new DeadlineCommand(deadlineArgs);
            case "event":
                ParseHelper.requireArgs(parts, 2, "task description");
                String[] eventArgs = TaskParser.parseEventCommand(parts[1].trim());
                return new EventCommand(eventArgs);

            case "find":
                ParseHelper.requireArgs(parts, 2, "search details");
                return new FindCommand(parts[1].trim());

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
* 4. 'todo 2025-11-11' causes error- UnsupportedTemporalTypeException. // done
* */