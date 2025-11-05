package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.core.CustomList;
import com.golden.core.Ui;
import com.golden.exceptions.BotException;
import com.golden.storage.Storage;

public abstract class Command {
    private final boolean isExit;

    protected Command(){
        this.isExit = false;
    }
    protected Command(boolean isExit){
        this.isExit = isExit;
    }

    public boolean isExit(){
        return this.isExit;
    }

    public final CommandResult execute(BotActions actions) throws BotException {
        CommandResult result = doExecute(actions);
        return result;
    }
    protected abstract CommandResult doExecute(BotActions actions)
            throws BotException;
}
