public final class LineParser {
    // overrides default public constructor method and makes it private
    // blocks anyone outside the class from instantiating new LineParser()
    private LineParser(){}

    public static boolean parseInput(String line, BotActions actions) {
        if (line == null) return true;

        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            Helper.printFormattedReply("You just entered whitespace! \nFeel free to chat whenever you are ready!");
            return true;
        }

        String[] parts = trimmedLine.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        switch (command) {
            case "bye":
                actions.sayBye();
                return false;

            case "list":
                actions.printList();
                return true;

            case "mark":
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    throw new IllegalArgumentException("Missing number after command");
                }
                try {
                    int listNumber = Integer.parseInt(parts[1].trim());
                    actions.mark(listNumber);
                    return true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Not a valid integer: " + parts[1].trim(), e);
                }

            case "unmark":
                actions.unmark(trimmedLine);
                return true;

            case "todo":
            case "deadline":
            case "event":
                actions.addToList(trimmedLine);
                return true;

            default:
                actions.echo(trimmedLine);
                return true;
        }
    }

}