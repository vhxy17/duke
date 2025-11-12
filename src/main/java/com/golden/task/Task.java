package com.golden.task;

import com.golden.exceptions.validationErrors.IllegalArgumentException;

public abstract class Task {
    private String description;
    private boolean isDone;
    protected Priority priority;

    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
        this.priority = Priority.LOW;
    }
    public Task(String description, boolean isDone) {
        this.description = description.trim();
        this.isDone = isDone;
        this.priority = Priority.LOW;
    }
    public Task(String description, boolean isDone, Priority priority) {
        this.description = description.trim();
        this.isDone = isDone;
        this.priority = (priority == null) ? Priority.LOW : priority;
    }
    protected String getTaskDescription() {
        return this.description;
    }
//    protected boolean validateTaskDescription(String description) throws IllegalArgumentException {
//        if (description.isBlank()){
//            throw new IllegalArgumentException("empty input. \nPlease provide a task description.");
//        }
//        return true;
//    }
    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }
    protected boolean isDone(){
        return isDone;
    }
    protected char renderStatusCharacter(){
        return isDone ? 'X' : ' ';
    };
    protected char renderStatusDigit(){
        return isDone ? '1' : '0';
    };
    protected abstract char renderTypeTag();
    public abstract String serialise();
    public abstract String toString();
    public Priority getPriority() {
        return priority;
    }
    public String toPriorityString(Priority priority) {
        if (priority == null) {
            return "LOW";
        }
        return priority.name();
    }
    public void setPriority(Priority p){
        this.priority = (p == null) ? Priority.LOW : p;
    }
}

/*
Fields: description, done (boolean).


renderForList() -> String
Base part: "[T][X] read book". Subclasses append extras.

serialize() -> String
Emit storage line (e.g., T | 1 | read book | June 6th for Deadline).

Getters/setters as needed.
 */