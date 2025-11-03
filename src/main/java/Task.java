public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
    }
    public Task(String description, boolean isDone) {
        this.description = description.trim();
        this.isDone = isDone;
    }
    protected String getTaskDescription() {
        return this.description;
    }
    // add setTaskDescription() method if task is editable
    protected void setIsDone(boolean isDone){
        this.isDone = isDone;
    }
    protected boolean isDone(){
        return isDone;
    }
    protected char renderStatusCharacter(){
        return isDone ? 'X' : ' ';
    };
    protected char renderStatusDigit(){
        return isDone ? '1' : '0';
    };
    protected abstract char renderTypeTag();
    public abstract String serialise();
    public abstract String toString();
}

/*
Fields: description, done (boolean).


renderForList() -> String
Base part: "[T][X] read book". Subclasses append extras.

serialize() -> String
Emit storage line (e.g., T | 1 | read book | June 6th for Deadline).

Getters/setters as needed.
 */