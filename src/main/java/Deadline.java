public class Deadline extends Task{
    private String endDate;

    public Deadline(String description, String by) {
        super(description);
        this.endDate = by;
    }
    protected void setBy(String by){
        this.endDate = by;
    }
    protected String getBy(){
        return endDate;
    }
    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", getDoneStatusIcon(), getTaskDescription(), getBy());
    }
}