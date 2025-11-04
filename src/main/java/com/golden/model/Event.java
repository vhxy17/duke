package com.golden.model;

public class Event extends Task{
    String startDate;
    String endDate;

    public Event(String description, String from, String to){
        super(description);
        this.startDate = from;
        this.endDate = to;
    }
    public Event(String description, boolean isDone, String from, String to){
        super(description, isDone);
        this.startDate = from;
        this.endDate = to;
    }

    protected void setStartDate(String start){
        this.startDate = start;
    }

    protected String getStartDate(){
        return startDate;
    }
    protected String getEndDate(){
        return endDate;
    }

    @Override
    protected char renderTypeTag(){
        return 'E';
    }
    @Override
    public String serialise(){
        return String.format("%c | %c | %s | %s | %s", renderTypeTag(), renderStatusDigit(),
                getTaskDescription(), getStartDate(), getEndDate());
    }
    @Override
    public String toString() {
        return String.format("[%c][%c] %s (from: %s to: %s)", renderTypeTag(),
                renderStatusCharacter(), getTaskDescription(), getStartDate(), getEndDate());
    }
}