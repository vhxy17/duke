package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;

public class DeadlineCommand extends Command {
    private final String taskString;
    private final String byString;
    private final String byPrefix = "/by ";
    private String validBy;

    public DeadlineCommand(String[] args) throws IllegalArgumentException {
        this.taskString = args[0];
        this.byString = args[1];
        validateTaskDescription(taskString);
        validatePrefixArgs(byString, byPrefix);
        validBy = extractArgFromPrefix(byString, byPrefix);
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.addDeadline(taskString, validBy);
        CommandResult result = new CommandResult(false);
        return result;
    }
}

