package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;

public class ListCommand extends Command{
    public ListCommand(){
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException {
        actions.printList();
        CommandResult result = new CommandResult(false);
        return result;
    }
}
