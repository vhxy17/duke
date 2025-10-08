public class Event extends Deadline{
    String start;

    public Event(String description, String start, String by){
        super(description, by);
        this.start = start;
    }

    protected void setStart(String start){
        this.start = start;
    }

    protected String getStart(){
        return start;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", getStatusIcon(), getTaskDescription(), getStart(), getBy());
    }
}