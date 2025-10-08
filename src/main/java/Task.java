public abstract class Task {
    private String description;

    public Task(String description) {
        this.description = description.trim();
    }

    protected String getTaskDescription() {
        return this.description;
    }

    public abstract String toString();
}