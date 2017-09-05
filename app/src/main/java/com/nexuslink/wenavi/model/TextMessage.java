package com.nexuslink.wenavi.model;

/**
 * Message切换后的代表消息的类
 * Created by aplrye on 17-9-4.
 */

public class TextMessage {
    private int type;
    private String messageContent;

    public TextMessage(int type, String messageContent) {
        this.type = type;
        this.messageContent = messageContent;
    }

    public int getType() {
        return type;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
