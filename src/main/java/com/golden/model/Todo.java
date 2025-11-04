package com.golden.model;

public class Todo extends Task{

    public Todo(String description) {
        super(description);
    }
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    protected char renderTypeTag(){
        return 'T';
    }
    @Override
    public String serialise(){
        return String.format("%c | %c | %s", renderTypeTag(), renderStatusDigit(), getTaskDescription());
    }
    @Override
    public String toString(){
        return String.format("[%c][%c] %s", renderTypeTag(), renderStatusCharacter(), getTaskDescription());
    }
}