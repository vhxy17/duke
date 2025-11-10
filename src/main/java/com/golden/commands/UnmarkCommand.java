package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;

public class UnmarkCommand extends Command {
    private final int number;

    public UnmarkCommand(int number) throws IllegalArgumentException {
        this.number = number;
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.unmark(number);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
