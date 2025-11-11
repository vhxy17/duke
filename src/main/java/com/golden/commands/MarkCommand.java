package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.validationErrors.IllegalArgumentException;

public class MarkCommand extends Command {
    private final int number;

    public MarkCommand(int number) {
        this.number = number;
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws IllegalArgumentException {
        actions.mark(number);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
