package com.golden.commands;

public final class CommandResult {
    public final boolean changed;   // did it mutate tasks? yes = save changes

    public CommandResult(boolean changed){
        this.changed = changed;
    }

//    public static CommandResult ok(String msg){
//        return new CommandResult(msg, false);
//    }
//
//    public static CommandResult changed(String msg){
//        return new CommandResult(msg, true);
//    }
}
