import java.util.Scanner;

public class Golden {
    private static String botName = "Golden";
    private static CustomList myList = new CustomList();

    private void greet() {
//        String message = String.format("Hello! I'm  %s.\nWhat can I do for you?", botName);
        Helper.formatReply(String.format("Hello! I'm  %s.\nWhat can I do for you?", botName));
    }
    private void sayBye() {
//        String message = "Bye. Hope to see you again soon!";
        Helper.formatReply("Bye. Hope to see you again soon!");
    }
    private void echo(String s) {
        Helper.formatReply(s);
    }
    private void addToList(String s){
        myList.addToList(s);
    }
    private void printAddedItem(String s){
        String message = "added: " + s;
        Helper.formatReply(message);
    }
    private void printList(){
        Helper.formatReply(myList.getList());
    }
    private void printMarkItem(int number, boolean isMark){
        if (isMark){
            String message = "Nice! I have marked this task as done:\n";
            message += myList.printTask(number);
            Helper.formatReply(message);
        } else {
            String message = "OK, I've marked this task as not done yet:\n";
            message += myList.printTask(number);
            Helper.formatReply(message);
        }
    }
    private void mark(String s){
        // split string by spaces
        String[] partsOfString = s.split(" ");
        if (partsOfString.length==2){
            try {
                int number = Integer.parseInt(partsOfString[1]);
                // Guard: check if number is in range
                if (Helper.checkNumberInRange(1, myList.getListSize(), number)){
                    myList.markTask(number);
                    printMarkItem(number, true);
                } else {
                    Helper.formatReply("Number out of range");
                    return;
                }
            } catch (NumberFormatException e){
                Helper.formatReply("Invalid number after 'mark'.");
            }
        } else
            System.out.println("mark input has the wrong format");
    }
    private void unmark(String s){
        // split string by spaces
        String[] partsOfString = s.split(" ");
        if (partsOfString.length==2){
            try {
                int number = Integer.parseInt(partsOfString[1]);
                // Guard: check if number is in range
                if (Helper.checkNumberInRange(1, myList.getListSize(), number)){
                    myList.unmarkTask(number);
                    printMarkItem(number, false);
                } else {
                    Helper.formatReply("Number out of range");
                    return;
                }
            } catch (NumberFormatException e){
                Helper.formatReply("Invalid number after 'unmark'.");
            }
        } else
            System.out.println("unmark input has the wrong format");
    }

    private void parseInput(String s) {
        if (s.contains("bye")){
            sayBye();
        } else if (s.equals("list")){
            printList();
        } else if (s.startsWith("mark ")){
            mark(s);
        } else if (s.startsWith("unmark ")){
            unmark(s);
        }
        else {
            addToList(s);
            printAddedItem(s);
        }
    }

    public static void main(String[] args) {
        Golden bot = new Golden();
        bot.greet();

        String line = "";
        Scanner input = new Scanner(System.in);
        while (!(line.contains("bye"))){
            line = input.nextLine();
            bot.parseInput(line);
        }
    }
}

// Level 0
//b) Implement an initial skeletal version of it that simply greets the user and exits.
//Example:
//
//____________________________________________________________
//Hello! I'm [YOUR CHATBOT NAME]
//What can I do for you?
//____________________________________________________________
//Bye. Hope to see you again soon!
//____________________________________________________________
//Horizontal lines are optional.

// Level 1
//____________________________________________________________
//Hello! I'm [YOUR CHATBOT NAME]
//What can I do for you?
//____________________________________________________________
//
//        list
//____________________________________________________________
//        list
//____________________________________________________________
//
//        blah
//____________________________________________________________
//        blah
//____________________________________________________________
//
//        bye
//____________________________________________________________
//Bye. Hope to see you again soon!
//____________________________________________________________