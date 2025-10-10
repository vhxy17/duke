import javax.sound.sampled.Line;
import java.util.Scanner;

// Golden == ChatBot
public class Golden {
    private final String botName = StaticConfig.APP_NAME;
    private final CustomList botList = new CustomList();        //list belongs to the chatbot
    private final BotActions actions = new BotActions(botName, botList);
//    private final LineParser parser = new LineParser();  // removed- decision to make LineParser a stateless, generic utility function

    public static void main(String[] args) {
        Golden bot = new Golden();
        bot.actions.greet();

        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running){
            String line = input.nextLine();
            running = LineParser.parseInput(line, bot.actions);
        }
    }
}