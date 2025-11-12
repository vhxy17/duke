package com.golden.core;

import com.golden.config.StaticConfig;
import com.golden.task.*;
import com.golden.exceptions.validationErrors.IllegalArgumentException;
import com.golden.util.ValidationHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomList implements Iterable<Task> {
    //    private static Task[] taskList;
    // Change from Task[] to ArrayList
    private static ArrayList<Task> taskList;
    private static int listSize;

    public CustomList(ArrayList<Task> tasks){
        this.taskList = (tasks == null) ? new ArrayList<>() : tasks;
    }
    protected int getSize(){
        return taskList.size();
    }
    private void addTask(Task task){
        // checks tasklist capacity before adding new task
        if (taskList.size() < StaticConfig.MAX_TASKS) {
            taskList.add(task);
        } else {
            System.out.println("List is full!");
        }
    }
    protected void addTodo(String taskDescription, Priority priority) throws IllegalArgumentException {
        addTask(new Todo(taskDescription, priority));
    }
    protected void addDeadline(String taskDescription, LocalDate endDate, Priority priority) throws IllegalArgumentException {
        addTask(new Deadline(taskDescription, endDate, priority));
    }
    protected void addEvent(String taskDescription, LocalDate startDate,
                            LocalDate endDate, Priority priority) throws IllegalArgumentException {
        addTask(new Event(taskDescription, startDate, endDate, priority));
    }
    protected String getList() {
        if (taskList.isEmpty()){
            return "Great news! You have no pending tasks right now.";
        }
        String message = "Here are the tasks in your list:\n";
        for (int i = 0; i < taskList.size(); i++){
            int number = i+1;
            message += String.format("%d.", number);
            message += taskList.get(i).toString();
            if (number != listSize){
                message += "\n";
            }
        }
        return message;
    }
    protected void markTask(int taskNumber, boolean isMark) throws IllegalArgumentException {
        ValidationHelper.isNumberInRange(1, taskList.size(), taskNumber);
        int taskIndex = taskNumber - 1;
        taskList.get(taskIndex).setIsDone(isMark);
    }
    protected Task getTask(int taskNumber) throws IllegalArgumentException {
        ValidationHelper.isNumberInRange(1, taskList.size(), taskNumber);
        int taskIndex = taskNumber - 1;
        return taskList.get(taskIndex);
    }
    protected void deleteTask(int taskNumber) throws IllegalArgumentException {
        ValidationHelper.isNumberInRange(1, taskList.size(), taskNumber);
        int taskIndex = taskNumber - 1;
        taskList.remove(taskIndex);
    }
    @Override
    public java.util.Iterator<Task> iterator() {
        return taskList.iterator();
    }

    public List<Task> findByKeyword(String keyword){
        List<Task> matches = new ArrayList<>();
        if (keyword == null || keyword.trim().isBlank()){
            return matches;
        }

        final String normalisedStr = keyword.toLowerCase();

        for (Task t : this){
            if (t == null){
                continue;
            }
            String desc = t.getTaskDescription();
            if (desc != null && desc.toLowerCase().contains(normalisedStr)){
                matches.add(t);
            }
        }
        return matches;
    }

}