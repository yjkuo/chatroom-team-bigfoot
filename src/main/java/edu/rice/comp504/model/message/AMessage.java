package edu.rice.comp504.model.message;

import java.util.Date;

/**
 * This class represents a single message within the app.
 */
public abstract class AMessage {
    private final int messageID;
    private final String chatRoomName;
    private String content;
    private final String sender;
    private String receiver;
    private final String type;
    private final Date timestamp;

    /**
     * Constructor.
     * @param messageID Unique message ID
     * @param chatRoomName chatroom to which this message belongs
     * @param content content of the message
     * @param sender sender of the message
     * @param type type of the message (normal or direct)
     */
    public AMessage(int messageID, String chatRoomName, String content, String sender, String receiver, String type) {
        this.messageID = messageID;
        this.chatRoomName = chatRoomName;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.timestamp = new Date();
    }

    /**
     * Return the ID of the message.
     * @return ID of the message.
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Return the type of the message.
     * @return type of the message.
     */
    public String getType() {
        return type;
    }

    /**
     * Return the timestamp of the message (sent or last edited).
     * @return timestamp of the message.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Return the chatroom name from which this message comes.
     * @return The chatroom name
     */
    public String getChatRoomName() {
        return chatRoomName;
    }

    /**
     * Return the sender of the message.
     * @return username of the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Return the content of the message.
     * @return string content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Reset the content of the message.
     * @param content string content to be set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }
}
