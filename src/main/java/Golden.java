import java.util.Scanner;

public class Golden {
    private static String botName = "Golden";
    private static String lineBreak = "---------------------------------------------";
    private static CustomList myList = new CustomList();

    private static String addIndentation(String s){
        // Add indentation at the start and after every newline
        return "\t" + s.replace("\n", "\n\t");
    }
    private void formatReply(String s){
        System.out.println(lineBreak);
        System.out.println(addIndentation(s));
        System.out.println(lineBreak);
    }
    private void greet() {
        String message = String.format("Hello! I'm  %s.\nWhat can I do for you?\n", botName);
        formatReply(message);
    }
    private void bye() {
        String message = "Bye. Hope to see you again soon!";
        formatReply(message);
    }
    private void echo(String s) {
        formatReply(s);
    }

    private void parseInput(String s) {
        if (s.contains("bye")){
            bye();
        } else if (s.equals("list")){
            formatReply(myList.printList());
        }
        else {
            myList.addToList(s);
            String message = "added: " + s;
            formatReply(message);
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