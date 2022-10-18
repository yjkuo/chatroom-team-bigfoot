package edu.rice.comp504.model.message;

import java.util.Date;

public class DirectMessage extends AMessage {
    private String receiver;

    public DirectMessage(int messageID, int chatRoomID, String content, String sender, String receiver, String type, Date timestamp) {
        super(messageID, chatRoomID, content, sender, type, timestamp);
        this.receiver = receiver;
    }
}
