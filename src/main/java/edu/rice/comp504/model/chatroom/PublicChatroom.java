package edu.rice.comp504.model.chatroom;

public class PublicChatroom extends AChatroom{
    /**
     * Constructor for AChatroom.
     *
     * @param name Unique name of chat room.
     * @param size Maximum size of the chat room.
     */
    public PublicChatroom(String name, int size) {
        super(name, "public", size);
    }
}
