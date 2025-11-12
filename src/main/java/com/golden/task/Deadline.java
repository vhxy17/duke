package com.golden.task;

import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.util.FormatHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDate endDate;

    public Deadline(String description, LocalDate by, Priority priority) {
        super(description);
        this.endDate = by;
        setPriority(priority);
    }
    public Deadline(String description, boolean isDone, LocalDate by) {
        super(description, isDone);
        this.endDate = by;
    }
    public Deadline(String description, boolean isDone, LocalDate by, Priority priority) {
        super(description, isDone, priority);
        this.endDate = by;
    }
    protected void setBy(LocalDate by){
        this.endDate = by;
    }
    protected LocalDate getEndDate(){
        return this.endDate;
    }
    protected String getSerialisedEndDate(){
        return endDate.format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
    }

    @Override
    protected char renderTypeTag() {
        return 'D';
    }
    @Override
    public String serialise(){
        return String.format("%c | %c | %s | %s | %s",
                renderTypeTag(), renderStatusDigit(), getTaskDescription(),
                getEndDate(), toPriorityString(getPriority()));
    }
    @Override
    public String toString() {
        return String.format("\t[%c][%c] %s (by: %s) (%s)",
                renderTypeTag(), renderStatusCharacter(), getTaskDescription(),
                FormatHelper.displayAsMMMdyyyy(getEndDate()), toPriorityString(getPriority()));
    }
}