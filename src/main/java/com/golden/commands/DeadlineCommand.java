package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.util.ParseHelper;

import java.time.LocalDate;

public class DeadlineCommand extends Command {
    private final String taskString;
    private final String byString;
    private final String byPrefix = "/by ";
    private LocalDate validByDate;

    public DeadlineCommand(String[] args) throws BotException {
        this.taskString = args[0];
        this.byString = args[1];
        validateTaskDescription(taskString);
        validatePrefixArgs(byString, byPrefix);
        String validByString = extractArgFromPrefix(byString, byPrefix);
        this.validByDate = ParseHelper.convertStringToDate(validByString);
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.addDeadline(taskString, validByDate);
        CommandResult result = new CommandResult(false);
        return result;
    }
}

