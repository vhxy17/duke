public class Event extends Task{
    String startDate;
    String endDate;

    public Event(String description, String from, String to){
        super(description);
        this.startDate = from;
        this.endDate = to;
    }

    protected void setStart(String start){
        this.startDate = start;
    }

    protected String getStartDate(){
        return startDate;
    }
    protected String getEndDate(){
        return endDate;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)",
                getDoneStatusIcon(), getTaskDescription(), getStartDate(), getEndDate());
    }
}