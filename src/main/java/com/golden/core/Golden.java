package com.golden.core;

import com.golden.config.StaticConfig;
import com.golden.exceptions.BotException;
import com.golden.parser.LineParser;
import com.golden.storage.Storage;
import com.golden.util.Helper;

import java.util.Scanner;

// Golden == ChatBot
public class Golden {
    private final String botName = StaticConfig.APP_NAME;
    private final CustomList tasks;        //list belongs to the chatbot
    private Storage storage;
    private BotActions actions;
    // removed- decision to make LineParser a stateless, generic utility function
    //    private final LineParser parser = new LineParser();

    private String getBotName(){
        return botName;
    }
    private void greet() {
        Helper.printFormattedReply(String.format("Hello! I'm  %s.\nWhat can I do for you?", getBotName()));
    }

    public Golden(String filePath) throws BotException {
        storage = new Storage(filePath);
        tasks = new CustomList(storage.loadFile());
        actions = new BotActions(botName, tasks, storage);
    }

    public void run(){
        greet();
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running){
            String line = input.nextLine();
            try {
                running = LineParser.parseInput(line, actions);
            } catch (BotException e){
                Helper.printFormattedReply(e.toString());
            }
        }
    }

    public static void main(String[] args) throws BotException {
        try {
            new Golden(StaticConfig.INIT_FILEPATH).run();
        } catch (BotException e) {
            Helper.printFormattedReply(e.toString());
        }
    }
}