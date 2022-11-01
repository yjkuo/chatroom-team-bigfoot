package edu.rice.comp504.model.message;

import java.util.Date;

public class DirectMessage extends AMessage {
    private String receiver;

    public DirectMessage(int messageID, String chatRoomName, String content, String sender, String receiver, String type, Date timestamp) {
        super(messageID, chatRoomName, content, sender, type);
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
