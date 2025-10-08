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
    public void addToList(String s){
        myList.addToList(s);
    }
    public void printAddedItem(String s){
        String message = "added: " + s;
        Helper.printFormattedReply(message);
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
    public void mark(String s){
        // split string by spaces
        String[] partsOfString = s.split(" ");
        if (partsOfString.length==2){
            try {
                int number = Integer.parseInt(partsOfString[1]);
                // Guard: check if number is in range
                if (Helper.checkNumberInRange(1, myList.getSize(), number)){
                    myList.markTask(number, true);
                    printMarkItem(number, true);
                } else {
                    Helper.printFormattedReply("Number out of range");
                    return;
                }
            } catch (NumberFormatException e){
                Helper.printFormattedReply("Invalid number after 'mark'.");
            }
        } else
            System.out.println("mark input has the wrong format");
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
