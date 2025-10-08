public class Todo extends Task{
    private boolean isDone;

    public Todo(String description) {
        super(description);
        this.isDone = false;
    }

    protected String getStatusIcon() {
        // mark 'done' task with X
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString(){
        return String.format("[T][%s] %s", getStatusIcon(), getTaskDescription());
    }

    protected void setIsDone(boolean isDone){
        this.isDone = isDone;
    }
}