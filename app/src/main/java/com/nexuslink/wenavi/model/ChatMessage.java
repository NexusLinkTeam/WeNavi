package com.nexuslink.wenavi.model;

/**
 * Created by 18064 on 2018/2/4.
 */

public class ChatMessage {
    private String textMessage;

    public ChatMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
