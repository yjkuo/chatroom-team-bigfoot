package edu.rice.comp504.model.chatroom;

public interface IChatroomFactory {

    IChatroomFactory makeFactory();

    AChatroom makeChatRoom(String name, String type, int size);
}
