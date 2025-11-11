package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.util.ParseHelper;
import com.golden.util.ValidationHelper;

import java.time.LocalDate;

public class EventCommand extends Command {
    private final String taskString;
    private final String fromString;
    private final String toString;
    private final String fromPrefix = "/from ";
    private final String toPrefix = "/to ";
    private LocalDate validFromDate;
    private LocalDate validToDate;

    public EventCommand(String[] args) throws BotException {
        this.taskString = args[0];
        this.fromString = args[1];
        this.toString = args[2];

        validateTaskDescription(taskString);
        validatePrefixArgs(fromString, fromPrefix);
        validatePrefixArgs(toString, toPrefix);
        String validFromString = extractArgFromPrefix(fromString, fromPrefix);
        String validToString = extractArgFromPrefix(toString, toPrefix);
        this.validFromDate = ParseHelper.convertStringToDate(validFromString);
        this.validToDate = ParseHelper.convertStringToDate(validToString);
        ValidationHelper.validateDateOrder(validFromDate, validToDate);
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.addEvent(taskString, validFromDate, validToDate);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
