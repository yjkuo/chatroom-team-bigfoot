package edu.rice.comp504.model;

/**
 * The interface of the message store, which will manage actions related to messages.
 */
public interface IMessageStore {

    /**
     * Send the message to either everyone in the chat room or a specific user.
     * @param content String content of the message
     * @param type String type of the message (direct or normal)
     * @param sender String sender of the message
     * @param receiver String receiver of the message
     * @param chatroomName String chatroomName in which the message will be sent.
     * @return Success or failure to deliver the message
     */
    void sendMessage(String type, String sender, String receiver, String content, String chatroomName);

    /**
     * Edit the message from a chat room.
     * @param messageID Int message ID of message that will be edited.
     * @param chatroomName String chat room name in which message resides.
     * @param content String content of the new message
     */
    void editMessage(int messageID, String chatroomName, String content);

    /**
     * Delete the message from a chat room.
     * @param messageID Int message ID of message that will be deleted.
     * @param chatroomName String chat room name in which message resides.
     */
    void deleteMessage(int messageID, String chatroomName);


}
