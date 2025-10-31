import Exceptions.ValidationErrors.IllegalArgumentException;
import Exceptions.ValidationErrors.IndexOutOfBoundsException;
import java.util.ArrayList;

public class CustomList {
//    private static Task[] taskList;
    // Change from Task[] to ArrayList
    private static ArrayList<Task> taskList;
    private static int listSize;

    public CustomList(){
        // instantiate an array with a preset max task limit
//        taskList = new Task[StaticConfig.MAX_TASKS];
        taskList = new ArrayList<Task>();
    }
    protected int getSize(){
        return taskList.size();
    }
    protected void addTask(Task task){
        // checks tasklist capacity before adding new task
        if (taskList.size() < StaticConfig.MAX_TASKS) {
            taskList.add(task);
        } else {
            System.out.println("List is full!");
        }
    }
    public void addTodo(String taskDescription){
        addTask(new Todo(taskDescription));
    }
    public void addDeadline(String taskDescription, String endDate){
        addTask(new Deadline(taskDescription, endDate));
    }
    public void addEvent(String taskDescription, String startDate, String endDate){
        addTask(new Event(taskDescription, startDate, endDate));
    }
    protected String getList() {
        if (taskList.size() == 0){
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
    protected void markTask(int taskNumber, boolean isMark) throws IllegalArgumentException{
        int taskIndex = taskNumber - 1;
        if (Helper.isNumberInRange(1, taskList.size(), taskNumber)){
            if (isMark){
                taskList.get(taskIndex).markDone();
            } else {
                taskList.get(taskIndex).markUndone();
            }
        } else {
            throw new IllegalArgumentException("Number out of range");
        }
    }
    protected Task getTask(int taskNumber) throws IllegalArgumentException {
        int taskIndex = taskNumber - 1;
        if (!Helper.isNumberInRange(1, taskList.size(), taskNumber)) {
            throw new IllegalArgumentException("Number out of range");
        }
        return taskList.get(taskIndex);
    }
    protected Task deleteTask(int taskNumber) throws IllegalArgumentException {
        int taskIndex = taskNumber - 1;
        if (!Helper.isNumberInRange(1, taskList.size(), taskNumber)) {
            throw new IllegalArgumentException("Number out of range");
        }
        return taskList.remove(taskIndex);
    }
}