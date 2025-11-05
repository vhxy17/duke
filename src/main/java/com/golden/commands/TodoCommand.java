package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;

public class TodoCommand extends Command {
    private final String args;

    public TodoCommand(String args) {
        this.args = args;
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.addToList(args);
        CommandResult result = new CommandResult(false);
        return result;
    }
}
