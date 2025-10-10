import java.util.regex.Pattern;

public class BotActions {
    private final String botName;
    private final CustomList myList;

    public BotActions(String name, CustomList list){
        this.botName = name;
        this.myList = list;
    }
    private String getBotName(){
        return botName;
    }
    public void greet() {
        Helper.printFormattedReply(String.format("Hello! I'm  %s.\nWhat can I do for you?", getBotName()));
    }
    public void sayBye() {
        Helper.printFormattedReply("Bye. Hope to see you again soon!");
    }
    public void echo(String s) {
        Helper.printFormattedReply(s);
    }
    public void addToList(String trimmedString){
        String[] parts = trimmedString.split("\\s+", 2);
        String taskType = parts[0].toLowerCase();    //leading whitespace already removed in previous step
        String taskDescription = parts[1].toLowerCase();

        if (taskType.equalsIgnoreCase("todo")){
            myList.addTodo(taskDescription);
            printAddedItem();
        } else if (taskType.equalsIgnoreCase("deadline")){
            String[] partsOfDescription = Pattern.compile("(?i)\\Q/by\\E").split(taskDescription, 2);
            String before = partsOfDescription[0];
            String after  = (partsOfDescription.length > 1) ? partsOfDescription[1] : "";
            after  = after.strip();
            myList.addDeadline(before, after);
            printAddedItem();
        } else if (taskType.equalsIgnoreCase("event")){
            String[] partsOfDescription = Pattern.compile("(?i)\\Q/from\\E|\\Q/to\\E").split(taskDescription, 3);
            String beforeFrom = partsOfDescription.length > 0 ? partsOfDescription[0].strip() : "";
            String beforeTo   = partsOfDescription.length > 1 ? partsOfDescription[1].strip() : "";
            String afterTo    = partsOfDescription.length > 2 ? partsOfDescription[2].strip() : "";
            myList.addEvent(beforeFrom, beforeTo, afterTo);
            printAddedItem();
        }
    }
    public void printAddedItem(){
        int itemIndex = myList.getSize() - 1;
        Helper.printFormattedReply("Got it. I've added this task:\n" + myList.getTask(itemIndex).toString()
                + "\nNow you have " + myList.getSize() + " task(s) in the list.");
    }
    public void printList(){
        Helper.printFormattedReply(myList.getList());
    }
    public void printMarkItem(int number, boolean isMark){
        if (isMark){
            String message = "Nice! I have marked this task as done:\n";
            message += myList.getTask(number);
            Helper.printFormattedReply(message);
        } else {
            String message = "OK, I've marked this task as not done yet:\n";
            message += myList.getTask(number);
            Helper.printFormattedReply(message);
        }
    }
    public void mark(int listNumber){
        // split string by spaces
//        String[] partsOfString = s.split(" ");
        if (Helper.checkNumberInRange(1, myList.getSize(), listNumber)){
            try {
                myList.markTask(listNumber, true);
                printMarkItem(listNumber, true);
            }
            catch (NumberFormatException e){
                Helper.printFormattedReply("Invalid number after 'mark'.");
            }
        } else
            Helper.printFormattedReply("Number out of range.");
    }

    public void unmark(String s){
        // split string by spaces
        String[] partsOfString = s.split(" ");
        if (partsOfString.length==2){
            try {
                int number = Integer.parseInt(partsOfString[1]);
                // Guard: check if number is in range
                if (Helper.checkNumberInRange(1, myList.getSize(), number)){
                    myList.markTask(number, false);
                    printMarkItem(number, false);
                } else {
                    Helper.printFormattedReply("Number out of range");
                    return;
                }
            } catch (NumberFormatException e){
                Helper.printFormattedReply("Invalid number after 'unmark'.");
            }
        } else
            System.out.println("unmark input has the wrong format");
    }
}
