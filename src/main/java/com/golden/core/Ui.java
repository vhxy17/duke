package com.golden.core;

import com.golden.config.StaticConfig;
import com.golden.util.FormatHelper;

import java.util.Scanner;

public class Ui {

    public Ui(){ }

    private String getBotName(){
        return StaticConfig.APP_NAME;
    }

    public void greet() {
        FormatHelper.printFormattedReply(String.format(
                "Hello! I'm  %s.\nWhat can I do for you?", getBotName()));
    }

    public String readCommand() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public void showError(String errorMsg){
        FormatHelper.printFormattedReply(errorMsg);
    }

    protected void printBotReply(String replyString){
        FormatHelper.printFormattedReply(replyString);
    }
}