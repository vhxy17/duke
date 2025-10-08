public class Deadline extends Todo{
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    protected void setBy(String by){
        this.by = by;
    }

    protected String getBy(){
        return by;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", getStatusIcon(), getTaskDescription(), getBy());
    }
}