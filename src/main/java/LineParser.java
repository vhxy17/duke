public final class LineParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private LineParser(){}

    public static boolean parseInput(String line, BotActions actions) {
        if (line == null) return true;

        String s = line.trim();
        if (s.isEmpty()) {
            Helper.printFormattedReply("You just entered whitespace! \nFeel free to chat whenever you are ready!");
            return true;
        }

        String[] parts = s.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        switch (cmd) {
            case "bye":
                actions.sayBye();
                return false;

            case "list":
                actions.printList();
                return true;

            case "mark":
                actions.mark(s);
                return true;

            case "unmark":
                actions.unmark(s);
                return true;

            case "todo":
            case "deadline":
            case "event":
                actions.addToList(s);
                return true;

            default:
                actions.addToList(s);
                actions.printAddedItem(s);
                return true;
        }
    }

}