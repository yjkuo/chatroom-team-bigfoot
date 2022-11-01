package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.user.UserFactory;

public class ChatroomFactory implements IChatroomFactory {
    private static ChatroomFactory ONLY;
    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    public static IChatroomFactory makeFactory() {
        if (ONLY == null ) {
            ONLY = new ChatroomFactory();
        }
        return ONLY;
    }

    /**
     * Make a chatroom.
     * @param name Unique name of the chatroom
     * @param type Type of the chatroom (public or private)
     * @param size Size of the chatroom
     * @return The created chatroom
     */
    public AChatroom makeChatRoom(String name, String type, int size) {
        return new Chatroom(name, type, size);
    }
}
