public class Task {
    private String description;
    private boolean isDone;

    protected Task(String description){
        this.description = description.trim();
        this.isDone = false;
    }
    private String getStatusIcon() {
        // mark 'done' task with X
        return (isDone ? "X" : " ");
    }
    protected String getTaskDescription(){
        return this.description;
    }
    protected String getTask(){
        return String.format("[%s] %s", getStatusIcon(), getTaskDescription());
    }
    protected void setIsDone(boolean isDone){
        this.isDone = isDone;
    }
}