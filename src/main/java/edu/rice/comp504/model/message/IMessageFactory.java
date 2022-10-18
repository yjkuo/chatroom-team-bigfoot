package edu.rice.comp504.model.message;

public interface IMessageFactory {

    IMessageFactory makeFactory();

    AMessage makeMessage(int messageID, int chatRoomID, String content, String sender, String type);
}
