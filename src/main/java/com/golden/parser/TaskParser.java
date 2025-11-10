package com.golden.parser;

import com.golden.exceptions.ParseException;
import com.golden.exceptions.parseErrors.MissingArgumentException;
import com.golden.exceptions.storageErrors.StorageFileParseException;
import com.golden.task.*;
import com.golden.util.ParseHelper;

import java.util.Locale;

public class TaskParser {
    private TaskParser(){}

    /* Parses storage line format: "E | 1 | read book | 2025-06-17 | 2025-07-07" into a Task */
    public static Task parseStorageLine(String rawLine, int lineNo) throws StorageFileParseException {
        if (rawLine == null){
            throw new StorageFileParseException("Line " + lineNo + ": null line.");
        }
        String line = rawLine.trim();
        // check for empty/comment lines
        if (line.isEmpty() || line.startsWith("#")){
            return null;    // signal "skip"
        }
        // Split by pipe; allow spaces around '|'   →  "T | 1 | read book"
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new StorageFileParseException("Line " + lineNo + ": expects at least 3 fields. Got: " + parts.length);
        }
        String type = parts[0].trim().toUpperCase(Locale.ROOT);
        String doneStr = parts[1].trim();
        boolean done;
        if ("1".equals(doneStr)) done = true;
        else if ("0".equals(doneStr)) done = false;
        else throw new StorageFileParseException("Line " + lineNo + ": done flag must be 0 or 1, got '" + doneStr + "'.");

        String desc = parts[2].trim();
        if (desc.isEmpty()) {
            throw new StorageFileParseException("Line " + lineNo + ": description cannot be empty.");
        }

        switch (type) {
            case "T":
                if (parts.length != 3) {
                    throw new StorageFileParseException("Line " + lineNo + ": Todo must have exactly 3 fields (T | done | desc).");
                }
                return new Todo(desc, done);

            case "D":
                if (parts.length != 4) {
                    throw new StorageFileParseException("Line " + lineNo + ": Deadline must have 4 fields (D | done | desc | by).");
                }
                String endDate = parts[3].trim();
                if (endDate.isEmpty()) {
                    throw new StorageFileParseException("Line " + lineNo + ": Deadline 'by' cannot be empty.");
                }
                return new Deadline(desc, done, endDate);

            case "E":
                if (parts.length != 5) {
                    throw new StorageFileParseException("Line " + lineNo + ": Event must have 5 fields (E | done | desc | from | to).");
                }
                String from = parts[3].trim();
                String to = parts[4].trim();
                if (from.isEmpty() || to.isEmpty()) {
                    throw new StorageFileParseException("Line " + lineNo + ": Event 'from' and 'to' cannot be empty.");
                }
                return new Event(desc, done, from, to);

            default:
                throw new StorageFileParseException("Line " + lineNo + ": unknown task type '" + type + "'. Expected T, D, or E.");
        }
    }

    public static String[] parseDeadlineCommand(String rawArgs) throws ParseException,
            MissingArgumentException {
        String[] deadlineArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(deadlineArgs, 2, "task description or /by");
        return deadlineArgs;
    }

    public static String[] parseEventCommand(String rawArgs) throws ParseException,
            MissingArgumentException {
        String[] eventArgs = ParseHelper.splitOnSlashSections(rawArgs.trim());
        ParseHelper.requireArgs(eventArgs, 3, "task description or /from or /to");
        return eventArgs;
    }
}
