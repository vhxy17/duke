public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
    }
    protected String getTaskDescription() {
        return this.description;
    }
    // add setTaskDescription() method if task is editable
    protected void markDone(){
        this.isDone = true;
    }
    protected void markUndone(){
        this.isDone = false;
    }
    protected boolean isDone(){
        return isDone;
    }
    protected String getDoneStatusIcon() {
        // mark 'done' task with X
        return (isDone ? "X" : " ");
    }

    public abstract String toString();
}