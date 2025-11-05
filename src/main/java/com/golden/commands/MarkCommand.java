package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;

public class MarkCommand extends Command {
    private final int number;

    public MarkCommand(int number) throws IllegalArgumentException {
        if (number == 0){
            throw new IllegalArgumentException("0 is not a valid task number!");
        }
        this.number = number;
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.mark(number);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
