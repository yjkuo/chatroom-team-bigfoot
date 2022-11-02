package edu.rice.comp504.model.message;

import edu.rice.comp504.model.chatroom.ChatroomFactory;

public class MessageFactory implements IMessageFactory {
    private static MessageFactory ONLY;
    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    public static IMessageFactory makeFactory() {
        if (ONLY == null ) {
            ONLY = new MessageFactory();
        }
        return ONLY;
    }

    /**
     * Make a message.
     * @param messageID unique id of message
     * @param chatRoomName unique name of chatroom
     * @param content content of the message
     * @param sender sender of the message
     * @param type type of the message (normal or direct)
     */
    public AMessage makeMessage(int messageID, String chatRoomName, String content, String sender, String receiver, String type) {
        return new NormalMessage(messageID, chatRoomName, content, sender, receiver, type);
    }
}
