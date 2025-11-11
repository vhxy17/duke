package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.exceptions.BotException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;

public abstract class Command {
    private final boolean isGoodbye;

    protected Command(){
        this.isGoodbye = false;
    }
    protected Command(boolean isExit){
        this.isGoodbye = isExit;
    }

    public boolean isExit(){
        return this.isGoodbye;
    }

    public final CommandResult execute(BotActions actions) throws BotException {
        CommandResult result = doExecute(actions);
        return result;
    }
    protected abstract CommandResult doExecute(BotActions actions)
            throws BotException;

    protected boolean validateTaskDescription(String description) throws IllegalArgumentException {
        if (description.isBlank()){
            throw new IllegalArgumentException("empty input. \nPlease provide a task description.");
        }
        return true;
    }
    protected boolean validatePrefixArgs(String string, String prefix) throws IllegalArgumentException{
        if (!string.startsWith(prefix)){
            throw new IllegalArgumentException(string + ". " +
                    "\n Please enter '" + prefix +" ...'");
        }
        return true;
    }
    protected String extractArgFromPrefix(String string, String prefix) {
        return string.trim().substring(prefix.length());
    }
}
