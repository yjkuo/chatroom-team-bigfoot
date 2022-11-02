package edu.rice.comp504.model.message;

import java.util.Date;

public class NormalMessage extends AMessage{
    public NormalMessage(int messageID, String chatRoomName, String content, String sender, String receiver, String type) {
        super(messageID, chatRoomName, content, sender, receiver, type);
    }
}
