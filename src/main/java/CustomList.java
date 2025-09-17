public class CustomList {
    private static Task[] taskList;
    private static int count;

    public CustomList(){
        // instantiates array of max 100 strings
        taskList = new Task[100];
        count = 0;
    }

    protected void addToList(String taskDescription){
        // checks tasklist capacity before adding new task
        if (count < taskList.length) {
            taskList[count] = new Task(taskDescription);
            count++;
        } else {
            System.out.println("List is full!");
        }
    }

    protected int getListSize(){
        return count;
    }

    protected String getList() {
        if (count == 0){
            return "Great news! You have no pending tasks right now.";
        }
        String message = "Here are the tasks in your list: \n";
        for (int i = 0; i < count; i++){
            int number = i+1;
            message += String.format("%d.", number);
            message += taskList[i].getTask();
            if (number != count){
                message += "\n";
            }
        }
        return message;
    }

    public void markTask(int taskNumber){
        taskList[taskNumber-1].markAsDone();
    }

    public void unmarkTask(int taskNumber){
        // Guard: check if number is in range
        taskList[taskNumber-1].unmarkAsDone();
    }

    public String printTask(int taskNumber){
        return taskList[taskNumber-1].getTask();
    }
}