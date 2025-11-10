package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.core.CustomList;
import com.golden.core.Ui;
import com.golden.exceptions.BotException;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
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

    protected boolean validateTaskDescription(String description) throws IllegalArgumentException {
        if (description.isBlank()){
            throw new IllegalArgumentException("Empty input. (Expected: Task Description)");
        }
        return true;
    }
    protected boolean validatePrefixArgs(String string, String prefix) throws IllegalArgumentException{
        if (!string.startsWith(prefix)){
            throw new IllegalArgumentException(string + ". (expected: '" + prefix +" ...')");
        }
        return true;
    }
    protected String extractArgFromPrefix(String string, String prefix) {
        return string.trim().substring(prefix.length());
//        validFrom = this.fromString.trim().substring("/from ".length());
//        validTo = this.toString.trim().substring("/to ".length());
    }
}
