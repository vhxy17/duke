package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;

public class ExitCommand extends Command {
    public ExitCommand(){
        super(true);
    }

    @Override
    protected CommandResult doExecute(BotActions actions)
            throws BotException{
        actions.sayBye();
        CommandResult result = new CommandResult(false);
        return result;
    }
}
