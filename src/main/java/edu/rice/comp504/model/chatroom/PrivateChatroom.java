package edu.rice.comp504.model.chatroom;

public class PrivateChatroom extends AChatroom{
    /**
     * Constructor for AChatroom.
     *
     * @param name Unique name of chat room.
     * @param size Maximum size of the chat room.
     */
    public PrivateChatroom(String name, int size) {
        super(name, "private", size);
    }
}
