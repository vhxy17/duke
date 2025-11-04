package com.golden.core;

import com.golden.config.StaticConfig;
import com.golden.util.Helper;

public class Ui {
    private String getBotName(){
        return StaticConfig.APP_NAME;
    }
    private void greet() {
        Helper.printFormattedReply(String.format("Hello! I'm  %s.\nWhat can I do for you?", getBotName()));
    }
    public Ui(){
        greet();
    }
}
