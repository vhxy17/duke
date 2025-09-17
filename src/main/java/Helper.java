public class Helper {
    private static final String lineBreak = "-------------------------------------------------------";

    // Static utility methods means no need to create a Helper object, can be called directly by Helper.formatReply(string)
    private static String addIndentation(String s){
        // Add indentation at the start and after every newline
        return "\t" + s.replace("\n", "\n\t");
    }
    public static void formatReply(String s){
        System.out.println(addIndentation(lineBreak));
        System.out.println(addIndentation(s));
        System.out.println(addIndentation(lineBreak));
    }
    public static boolean checkNumberInRange(int lowerLimit, int upperLimit, int number ){
        if (number < lowerLimit || number > upperLimit){
            return false;
        }
        return true;
    }
}
