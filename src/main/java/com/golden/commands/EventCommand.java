package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;

public class EventCommand extends Command {
    private final String taskString;
    private final String fromString;
    private final String toString;
    private final String fromPrefix = "/from ";
    private final String toPrefix = "/to ";
    private String validFrom;
    private String validTo;

    public EventCommand(String[] args) throws IllegalArgumentException {
        this.taskString = args[0];
        this.fromString = args[1];
        this.toString = args[2];

        validateTaskDescription(taskString);
        validatePrefixArgs(fromString, fromPrefix);
        validatePrefixArgs(toString, toPrefix);
        validFrom = extractArgFromPrefix(fromString, fromPrefix);
        validTo = extractArgFromPrefix(toString, toPrefix);

    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.addEvent(taskString, validFrom, validTo);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
