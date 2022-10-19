package edu.rice.comp504.model.chatroom;

/**
 * A factory that will make chatroom.
 */
public interface IChatroomFactory {

    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    IChatroomFactory makeFactory();

    /**
     * Make a chatroom.
     * @param name Unique name of the chatroom
     * @param type Type of the chatroom (public or private)
     * @param size Size of the chatroom
     * @return The created chatroom
     */
    AChatroom makeChatRoom(String name, String type, int size);
}
