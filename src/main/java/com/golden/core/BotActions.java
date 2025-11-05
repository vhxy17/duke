package com.golden.core;

import java.util.regex.Pattern;

import com.golden.storage.Storage;
import com.golden.util.Helper;
import com.golden.exceptions.parseErrors.IllegalArgumentException;
import com.golden.exceptions.storageErrors.StorageFileParseException;

public class BotActions {
    CustomList myList;
    Storage storage;

    public BotActions(CustomList list, Storage storage) {
        this.myList = list;
        this.storage = storage;
    }

    private int getLastTaskNumber() {
        return myList.getSize();
    }

    public void sayBye() {
        Helper.printFormattedReply("Bye. Hope to see you again soon!");
    }

    public void echo(String s) {
        Helper.printFormattedReply(s);
    }

    private void printAddedItem() throws IllegalArgumentException {
        Helper.printFormattedReply("Got it. I've added this task:\n"
                + myList.getTask(getLastTaskNumber()).toString()
                + "\nNow you have " + myList.getSize() + " task(s) in the list.");
    }

    public void addToList(String trimmedString) throws IllegalArgumentException,
            StorageFileParseException {
                String[] parts = trimmedString.split("\\s+", 2);
                String taskType = parts[0].toLowerCase();    //leading whitespace already removed in previous step
                String taskDescription = parts[1].toLowerCase();

                if (taskType.equalsIgnoreCase("todo")) {
                    myList.addTodo(taskDescription);
                } else if (taskType.equalsIgnoreCase("deadline")) {
                    String[] partsOfDescription = Pattern.compile("(?i)\\Q/by\\E").split(taskDescription, 2);
                    String before = partsOfDescription[0];
                    String after = (partsOfDescription.length > 1) ? partsOfDescription[1] : "";
                    after = after.strip();
                    myList.addDeadline(before, after);
                } else if (taskType.equalsIgnoreCase("event")) {
                    String[] partsOfDescription = Pattern.compile("(?i)\\Q/from\\E|\\Q/to\\E").split(taskDescription, 3);
                    String beforeFrom = partsOfDescription.length > 0 ? partsOfDescription[0].strip() : "";
                    String beforeTo = partsOfDescription.length > 1 ? partsOfDescription[1].strip() : "";
                    String afterTo = partsOfDescription.length > 2 ? partsOfDescription[2].strip() : "";
                    myList.addEvent(beforeFrom, beforeTo, afterTo);
                }
                printAddedItem();
                storage.writeToFile(myList);
    }


    public void printList() {
        Helper.printFormattedReply(myList.getList());
    }

    public void printMarkItem(int number, boolean isMark) throws IllegalArgumentException {
        if (isMark) {
            String message = "Nice! I have marked this task as done:\n";
            message += myList.getTask(number);
            Helper.printFormattedReply(message);
        } else {
            String message = "OK, I've marked this task as not done yet:\n";
            message += myList.getTask(number);
            Helper.printFormattedReply(message);
        }
    }
    public void printDeletedItem(String taskDescription) {
        String message = "Done! I have removed this task:\n";
        message += taskDescription;
        message += "\nYou have " + myList.getSize() + " tasks in the list.";
        Helper.printFormattedReply(message);
    }

    public void mark(int listNumber) throws IllegalArgumentException {
        myList.markTask(listNumber, true);
        printMarkItem(listNumber, true);
    }

    public void unmark(int listNumber) throws IllegalArgumentException  {
            myList.markTask(listNumber, false);
            printMarkItem(listNumber, false);
    }

    public void delete(int listNumber) throws IllegalArgumentException, StorageFileParseException {
        String deletedTask = myList.getTask(listNumber).toString();
        myList.deleteTask(listNumber);
        storage.writeToFile(myList);
        printDeletedItem(deletedTask);
    }
}
