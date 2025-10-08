public class CustomList {
    private static Todo[] taskList;
    private int size;

    public CustomList(){
        // instantiates array of max 100 strings
        taskList = new Todo[StaticConfig.MAX_TASKS];
        size = 0;
    }
    protected int getSize(){
        return size;
    }
    protected void addToList(String taskDescription){
        // checks tasklist capacity before adding new task
        if (size < taskList.length) {
            taskList[size] = new Todo(taskDescription);
            System.out.println("Got it. I've added this task:\n" + taskList[size].toString()
                + "\n Now you have " + size + " task(s) in the list.");
            size++;
        } else {
            System.out.println("List is full!");
        }
    }
    protected String getList() {
        if (size == 0){
            return "Great news! You have no pending tasks right now.";
        }
        String message = "Here are the tasks in your list:\n";
        for (int i = 0; i < size; i++){
            int number = i+1;
            message += String.format("%d.", number);
            message += taskList[i].toString();
            if (number != size){
                message += "\n";
            }
        }
        return message;
    }
    protected void markTask(int taskNumber, boolean isMark){
        if (taskNumber < 1 || taskNumber > size){
            throw new IndexOutOfBoundsException();
        }
        taskList[taskNumber-1].setIsDone(isMark);
    }
    protected String getTask(int taskNumber){
        if (taskNumber < 1 || taskNumber > size){
            throw new IndexOutOfBoundsException();
        }
        return taskList[taskNumber-1].toString();
    }
}