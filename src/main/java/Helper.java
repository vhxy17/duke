public final class Helper {         // 'final' is used to prevent a new Helper class
    private static final String lineBreak = "-------------------------------------------------------";

    // override default public constructor method and makes it private; prevents instantiation by other classes
    private Helper(){}

    // Static utility methods means no need to create a Helper object, can be called directly: e.g. Helper.printReply(string)
    private static String addIndentation(String s){
        // Add indentation at the start and after every new line
        return "\t" + s.replace("\n", "\n\t");
    }
    private static String formatReply(String string){
        return addIndentation(lineBreak) + "\n"
                + addIndentation(string) + "\n"
                + addIndentation(lineBreak);
    }
    public static void printFormattedReply(String rawReply){
        System.out.println(formatReply(rawReply));
    }
    public static boolean checkNumberInRange(int lowerLimit, int upperLimit, int number ){
        if (number < lowerLimit || number > upperLimit){
            return false;
        }
        return true;
    }
}
