package com.golden.commands;

import com.golden.core.BotActions;
import com.golden.core.Ui;
import com.golden.exceptions.BotException;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.task.Priority;
import com.golden.util.ParseHelper;

import java.time.LocalDate;

public class DeadlineCommand extends Command {
    private final String byPrefix = "/by ";
    private final String priorityPrefix = "/priority ";

    private final String taskString;
    private final String byString;
    private final String priorityString;

    private LocalDate byDate;
    private Priority priority;

    public DeadlineCommand(String[] args) throws BotException {
        this.taskString = args[0];
        this.byString = args[1];
        this.priorityString = args[2];

//        validateTaskDescription(taskString);
        validatePrefixArgs(byString, byPrefix);
        validatePrefixArgs(priorityString, priorityPrefix);
        String validByString = extractArgFromPrefix(byString, byPrefix);
        String validPriorityString = extractArgFromPrefix(priorityString, priorityPrefix);
        this.byDate = ParseHelper.convertStringToDate(validByString);
        this.priority = ParseHelper.convertStringToPriority(validPriorityString);
    }

    @Override
    public void execute(BotActions actions, Ui ui) throws BotException {
        actions.addDeadline(taskString, byDate, priority);
        ui.printBotReply(actions.constructAddTaskMsg());
    }
}

