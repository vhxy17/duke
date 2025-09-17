public class Task {
    private String description;
    private boolean isDone;

    public Task(String s){
        this.description = s;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
    public String getTask(){
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    public void markAsDone(){
        this.isDone = true;
    }
    public void unmarkAsDone(){
        this.isDone = false;
    }
}