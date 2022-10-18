package edu.rice.comp504.model.message;

import java.util.Date;

public abstract class AMessage {
    private final int messageID;
    private final String chatRoomName;
    private String content;
    private final String sender;
    private final String type;
    private final Date timestamp;

    public AMessage(int messageID, String chatRoomName, String content, String sender, String type){
        this.messageID = messageID;
        this.chatRoomName = chatRoomName;
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.timestamp = new Date();
    }

    public int getMessageID() {
        return messageID;
    }

    public String getType() {
        return type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
