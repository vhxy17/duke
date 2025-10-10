public class CustomList {
    private static Task[] taskList;
    private int listSize;

    public CustomList(){
        // instantiates an array with a preset max task limit
        taskList = new Task[StaticConfig.MAX_TASKS];
        listSize = 0;
    }
    protected int getSize(){
        return listSize;
    }
    protected void addTask(Task task){
        // checks tasklist capacity before adding new task
        if (listSize < taskList.length) {
            taskList[listSize] = task;
            listSize++;
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
        if (listSize == 0){
            return "Great news! You have no pending tasks right now.";
        }
        String message = "Here are the tasks in your list:\n";
        for (int i = 0; i < listSize; i++){
            int number = i+1;
            message += String.format("%d.", number);
            message += taskList[i].toString();
            if (number != listSize){
                message += "\n";
            }
        }
        return message;
    }
    protected void markTask(int taskNumber, boolean isMark){
        if (taskNumber < 1 || taskNumber > listSize){
            throw new IndexOutOfBoundsException();
        }
        if (isMark){
            taskList[taskNumber-1].markDone();
        } else {
            taskList[taskNumber-1].markUndone();            
        }
    }
    protected Task getTask(int taskIndex){
        if (taskIndex < 0 || taskIndex > listSize){
            throw new IndexOutOfBoundsException();
        }
        return taskList[taskIndex];
    }
}