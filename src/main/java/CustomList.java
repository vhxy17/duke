public class CustomList {
    private static String[] list;
    private static int count;

    public CustomList(){
        list = new String[100];
        count = 0;
    }

    public void addToList(String s){
        if (count < list.length) {
            list[count] = s;  // add to next free slot
            count++;
        } else {
            System.out.println("List is full!");
        }
    }

    public static String printList() {
        String message = "";
        for (int i = 0; i < count; i++) {
            int number = i+1;
            message += (number + ". " + list[i]);
            if (number != count){
                message += "\n";
            }
        }
        return message;
    }
}