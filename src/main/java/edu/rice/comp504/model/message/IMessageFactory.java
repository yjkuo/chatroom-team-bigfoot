package edu.rice.comp504.model.message;

/**
 * A factory that will make messages.
 */
public interface IMessageFactory {

    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    IMessageFactory makeFactory();

    /**
     * Make a message.
     * @param messageID unique id of message
     * @param chatRoomName unique name of chatroom
     * @param content content of the message
     * @param sender sender of the message
     * @param type type of the message (normal or direct)
     */
    AMessage makeMessage(int messageID, String chatRoomName, String content, String sender, String type);
}
