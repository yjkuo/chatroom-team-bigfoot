package edu.rice.comp504.model;

public class MessageStore implements IMessageStore{
    private static MessageStore ONLY;

    private MessageStore(){};

    public static MessageStore makeStore() {
        if (ONLY == null) {
            ONLY = new MessageStore();
        }
        return ONLY;
    }

    @Override
    public void sendMessage(String type, String sender, String receiver, String content, String chatroomName) {

    }

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {

    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {

    }
}
