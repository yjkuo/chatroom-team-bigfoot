package edu.rice.comp504.model.message;

import java.util.Date;

public class NormalMessage extends AMessage{
    public NormalMessage(int messageID, int chatRoomID, String content, String sender, String type, Date timestamp) {
        super(messageID, chatRoomID, content, sender, type, timestamp);
    }
}
