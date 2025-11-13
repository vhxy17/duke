package com.golden.parser;

import com.golden.task.Task;
import com.golden.task.Todo;
import com.golden.task.Deadline;
import com.golden.task.Event;
import com.golden.task.Priority;
import com.golden.util.ParseHelper;
import com.golden.util.ValidationHelper;
import com.golden.exceptions.BotException;
import com.golden.exceptions.ParseException;
import com.golden.exceptions.storageErrors.StorageFileParseException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;

import java.time.LocalDate;
import java.util.Locale;

public class TaskParser {
    private TaskParser(){}

    private static boolean isDone(String doneStr, int lineNo) throws StorageFileParseException {
        if ("1".equals(doneStr)) {
            return true;
        }
        else if ("0".equals(doneStr)) {
            return false;
        } else {
            throw new StorageFileParseException(String.format(
                "Line: %d: done flag must be 0 or 1. Got '%s'.", lineNo, doneStr));
        }
    }
    /** Parses a line in the following format: '[Task type] | [isDone] | [Task Description] | [Date 1] | [Date 2]'
     *  into a task (Todo, Deadline, Event)
     *
     * @return  a Task object that could be a 'Todo', 'Deadline' or 'Event' or a {@code Throwable} Exception.
     *  */
    public static Task parseStorageLine(String rawLine, int lineNo) throws BotException {
        if (rawLine == null){
            throw new StorageFileParseException(
                    String.format("Line %d: null line.", lineNo));
        }
        String line = rawLine.trim();
        // check for empty/comment lines
        if (line.isEmpty() || line.startsWith("#")){
            return null;    // signal "skip"
        }
        // Split by pipe; allow spaces around '|'   →  "T | 1 | read book | LOW"
        String[] parts = line.split("\\s*\\|\\s*");
        int minimumParts = 4;
        if (parts.length < minimumParts) {
            throw new StorageFileParseException(String.format(
                    "Line %d: expects at least %d fields. Got: %d", lineNo, minimumParts, parts.length));
        }
        String type = parts[0].trim().toUpperCase(Locale.ROOT);
        String doneStr = parts[1].trim();
        String desc = parts[2].trim();
        String priorityString = parts[parts.length - 1];
        Priority priority = ParseHelper.convertStringToPriority(priorityString);
        boolean isDone = isDone(doneStr, lineNo);

        if (desc.isEmpty()) {
            throw new StorageFileParseException(String.format(
                    "Line %d: description cannot be empty.", lineNo));
        }

        switch (type) {
            case "T":
                if (parts.length != 4) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Todo must have this format: 'T | isDone | task | priority'.", lineNo));
                }
                return new Todo(desc, isDone, priority);

            case "D":
                if (parts.length != 5) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Deadline must have this format: 'D | isDone | task | by | priority'", lineNo));
                }
                String endDateString = parts[3].trim();
                if (endDateString.isEmpty()) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Deadline 'by' cannot be empty.", lineNo));
                }
                return new Deadline(desc, isDone, ParseHelper.convertStringToDate(endDateString), priority);

            case "E":
                if (parts.length != 6) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Event must have this format: 'E | isDone | task | from | to | priority'.", lineNo));
                }
                String fromString = parts[3].trim();
                String toString = parts[4].trim();
                if (fromString.isEmpty() || toString.isEmpty()) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Event 'from' and/or 'to' cannot be empty.", lineNo));
                }
                LocalDate from = ParseHelper.convertStringToDate(fromString);
                LocalDate to = ParseHelper.convertStringToDate(toString);
                try {
                    ValidationHelper.validateDateOrder(from, to);
                } catch (IllegalArgumentException e){
                    throw new StorageFileParseException(String.format(
                            "Line %d: 'From' date is before 'To' date.", lineNo));
                }
                return new Event(desc, isDone, from, to, priority);

            default:
                throw new StorageFileParseException(String.format(
                        "Line %d: unknown task type '%s'. \nValid task types: 'T', 'D', or 'E'.", lineNo, type));
        }
    }

    /** Parses a String and returns its parts: '[String before /by]' and '[String from /by]', '[String from /priority]'.
     *
     * @return  a String array or a {@code Throwable} Exception.
     *  */
    public static String[] parseDeadlineCommand(String rawArgs) throws ParseException {
        String[] deadlineArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(deadlineArgs, 3, "task description, deadline or priority");
        return deadlineArgs;
    }

    /** Parses a String and returns its parts: '[String before /from]', '[String from /from],
     * '[String from /to], '[String from /priority]'.
     *
     * @return  a String array or a {@code Throwable} Exception.
     *  */
    public static String[] parseEventCommand(String rawArgs) throws ParseException{
        String[] eventArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(eventArgs, 4, "task description, from, to, or priority");
        return eventArgs;
    }

    /** Parses a String and returns its parts: '[String before /priority]' and '[String from /priority]'.
     *
     * @return  a String array or a {@code Throwable} Exception.
     *  */
    public static String[] parseTodoCommand(String rawArgs) throws ParseException{
        String[] todoArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(todoArgs, 2, "task description or priority");
        return todoArgs;
    }
}


// stop here: parseXXCommand always checks for required arguments- mandates that priority is given. how to develop
// a system where /priority can be an optional input?