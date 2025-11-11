package com.golden.parser;

import com.golden.exceptions.BotException;
import com.golden.exceptions.ParseException;
import com.golden.exceptions.parseErrors.MissingArgumentException;
import com.golden.exceptions.storageErrors.StorageFileParseException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.task.*;
import com.golden.util.ParseHelper;
import com.golden.util.ValidationHelper;

import java.time.LocalDate;
import java.util.Locale;

public class TaskParser {
    private TaskParser(){}

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
        // Split by pipe; allow spaces around '|'   →  "T | 1 | read book"
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new StorageFileParseException(String.format(
                    "Line %d: expects at least 3 fields. Got: %d", lineNo, parts.length));
        }
        String type = parts[0].trim().toUpperCase(Locale.ROOT);
        String doneStr = parts[1].trim();
        boolean done;
        if ("1".equals(doneStr)) {
            done = true;
        }
        else if ("0".equals(doneStr)) {
            done = false;
        }
        else throw new StorageFileParseException(String.format(
                "Line: %d: done flag must be 0 or 1. Got '%s'.", lineNo, doneStr));

        String desc = parts[2].trim();
        if (desc.isEmpty()) {
            throw new StorageFileParseException(String.format(
                    "Line %d: description cannot be empty.", lineNo));
        }

        switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Todo must have this format: 'T | done | desc'.", lineNo));
                }
                return new Todo(desc, done);

            case "D":
                if (parts.length != 4) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Deadline must have this format: 'D | isDone | desc | by'", lineNo));
                }
                String endDateString = parts[3].trim();
                if (endDateString.isEmpty()) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Deadline 'by' cannot be empty.", lineNo));
                }
                return new Deadline(desc, done, ParseHelper.convertStringToDate(endDateString));

            case "E":
                if (parts.length != 5) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Event must have this format: 'E | isDone | desc | from | to'.", lineNo));
                }
                String fromString = parts[3].trim();
                String toString = parts[4].trim();
                if (fromString.isEmpty() || toString.isEmpty()) {
                    throw new StorageFileParseException(String.format(
                            "Line %d: Event 'from' and/or 'to' cannot be empty.", lineNo));
                }
                LocalDate from = LocalDate.parse(fromString);
                LocalDate to = LocalDate.parse(toString);
                try {
                    ValidationHelper.validateDateOrder(from, to);
                } catch (IllegalArgumentException e){
                    throw new StorageFileParseException(String.format(
                            "Line %d: 'From' date is before 'To' date.", lineNo));
                }
                return new Event(desc, done, from, to);

            default:
                throw new StorageFileParseException(String.format(
                        "Line %d: unknown task type '%s'. \nValid task types: 'T', 'D', or 'E'.", lineNo, type));
        }
    }

    /** Parses a String and returns its parts: '[String before /by]' and '[String from /by]'.
     *
     * @return  a String array or a {@code Throwable} Exception.
     *  */
    public static String[] parseDeadlineCommand(String rawArgs) throws ParseException,
            MissingArgumentException {
        String[] deadlineArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(deadlineArgs, 2, "task description or '/by...'");
        return deadlineArgs;
    }


    /** Parses a String and returns its parts: '[String before /from]', '[String from /from], and '[String from /to]'.
     *
     * @return  a String array or a {@code Throwable} Exception.
     *  */
    public static String[] parseEventCommand(String rawArgs) throws ParseException,
            MissingArgumentException {
        String[] eventArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(eventArgs, 3, "task description or '/from...' or '/to...'");
        return eventArgs;
    }
}
