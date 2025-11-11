package com.golden.task;

import com.golden.util.FormatHelper;

import java.time.LocalDate;

public class Event extends Task{
    LocalDate startDate;
    LocalDate endDate;

    public Event(String description, LocalDate from, LocalDate to){
        super(description);
        this.startDate = from;
        this.endDate = to;
    }
    public Event(String description, boolean isDone, LocalDate from, LocalDate to){
        super(description, isDone);
        this.startDate = from;
        this.endDate = to;
    }

    protected void setStartDate(LocalDate start){
        this.startDate = start;
    }

    protected LocalDate getStartDate(){
        return this.startDate;
    }
    protected LocalDate getEndDate(){
        return this.endDate;
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
        return String.format("\t[%c][%c] %s (from: %s to: %s)", renderTypeTag(),
                renderStatusCharacter(), getTaskDescription(),
                FormatHelper.displayAsMMMdyyyy(getStartDate()), FormatHelper.displayAsMMMdyyyy(getEndDate()));
    }
}