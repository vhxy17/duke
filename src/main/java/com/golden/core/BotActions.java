package com.golden.core;

import com.golden.exceptions.BotException;
import com.golden.storage.Storage;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.exceptions.storageErrors.StorageFileParseException;
import java.time.LocalDate;

public class BotActions {
    CustomList myList;
    Storage storage;
    Ui ui;

    public BotActions(CustomList list, Storage storage, Ui ui) {
        this.myList = list;
        this.storage = storage;
        this.ui = ui;
    }

    private int getLastTaskNumber() {
        return myList.getSize();
    }

    public void sayBye() {
        ui.printBotReply("Goodbye! See you soon!");
    }

    public void echo(String s) {
        ui.printBotReply(s);
    }

    public void printList() {
        ui.printBotReply(myList.getList());
    }

    private void displayAddedItem() throws IllegalArgumentException {
        String message = "Got it. I've added this task:\n"
                + myList.getTask(getLastTaskNumber()).toString()
                + "\nNow you have " + myList.getSize() + " task(s) in the list.";

        ui.printBotReply(message);
    }

    public void displayMarkItem(int number, boolean isMark) throws IllegalArgumentException {
        if (isMark) {
            String message = "Nice! I have marked this task as done:\n";
            message += myList.getTask(number);
            ui.printBotReply(message);
        } else {
            String message = "OK, I've marked this task as not done yet:\n";
            message += myList.getTask(number);
            ui.printBotReply(message);
        }
    }

    public void displayDeletedItem(String taskDescription) {
        String message = "Done! I have removed this task:\n";
        message += taskDescription;
        message += "\nYou have: " + myList.getSize() + " pending tasks.";
        ui.printBotReply(message);
    }

    public void mark(int listNumber) throws IllegalArgumentException {
        myList.markTask(listNumber, true);
        displayMarkItem(listNumber, true);
    }

    public void unmark(int listNumber) throws IllegalArgumentException  {
        myList.markTask(listNumber, false);
        displayMarkItem(listNumber, false);
    }

    public void addTodo(String args) throws BotException {
        myList.addTodo(args);
        storage.writeToFile(myList);
        displayAddedItem();
    }

    public void addDeadline(String task, LocalDate deadlineBy) throws BotException {
        myList.addDeadline(task, deadlineBy);
        storage.writeToFile(myList);
        displayAddedItem();
    }
    public void addEvent(String task, LocalDate eventFrom, LocalDate eventTo) throws BotException {
        myList.addEvent(task, eventFrom, eventTo);
        storage.writeToFile(myList);
        displayAddedItem();
    }

    public void delete(int listNumber) throws IllegalArgumentException, StorageFileParseException {
        String deletedTask = myList.getTask(listNumber).toString();
        myList.deleteTask(listNumber);
        storage.writeToFile(myList);
        displayDeletedItem(deletedTask);
    }
}
