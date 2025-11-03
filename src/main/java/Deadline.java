public class Deadline extends Task{
    private String endDate;

    public Deadline(String description, String by) {
        super(description);
        this.endDate = by;
    }
    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.endDate = by;
    }
    protected void setBy(String by){
        this.endDate = by;
    }
    protected String getEndDate(){
        return endDate;
    }

    @Override
    protected char renderTypeTag() {
        return 'D';
    }
    @Override
    public String serialise(){
        return String.format("%c | %c | %s | %s", renderTypeTag(), renderStatusDigit(), getTaskDescription(), getEndDate());
    }
    @Override
    public String toString() {
        return String.format("[%c][%c] %s (by: %s)", renderTypeTag(), renderStatusCharacter(), getTaskDescription(), getEndDate());
    }
}