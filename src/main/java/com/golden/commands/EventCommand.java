package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.core.Ui;
import com.golden.exceptions.BotException;
import com.golden.task.Priority;
import com.golden.util.ParseHelper;
import com.golden.util.ValidationHelper;

import java.time.LocalDate;

public class EventCommand extends Command {
    private final String fromPrefix = "/from";
    private final String toPrefix = "/to";
    private final String priorityPrefix = "/priority";

    private final String taskString;
    private final String fromString;
    private final String toString;
    private final String priorityString;

    private LocalDate fromDate;
    private LocalDate toDate;
    private Priority priority;

    public EventCommand(String[] args) throws BotException {
        this.taskString = args[0].trim();
        this.fromString = args[1].trim();
        this.toString = args[2].trim();
        this.priorityString = args[3].trim();

        validatePrefixArgs(fromString, fromPrefix);
        validatePrefixArgs(toString, toPrefix);
        validatePrefixArgs(priorityString, priorityPrefix);

        String validFromString = extractArgFromPrefix(fromString, fromPrefix);
        String validToString = extractArgFromPrefix(toString, toPrefix);
        String validPriorityString = extractArgFromPrefix(priorityString, priorityPrefix);

        this.fromDate = ParseHelper.convertStringToDate(validFromString);
        this.toDate = ParseHelper.convertStringToDate(validToString);
        this.priority = ParseHelper.convertStringToPriority(validPriorityString);
        ValidationHelper.validateDateOrder(fromDate, toDate);
    }

    @Override
    public void execute(BotActions actions, Ui ui) throws BotException {
        actions.addEvent(taskString, fromDate, toDate, priority);
        ui.printBotReply(actions.constructAddTaskMsg());
    }
}
