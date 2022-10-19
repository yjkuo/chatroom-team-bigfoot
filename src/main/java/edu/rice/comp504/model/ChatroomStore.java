package edu.rice.comp504.model;

import edu.rice.comp504.controller.ChatAppController;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatroomStore implements IChatroomStore{
    /**
     * List of chat rooms on the server.
     */
    private Map<String, AChatroom> chatroomList = new ConcurrentHashMap<>();
    private static ChatroomStore ONLY;

    private ChatroomStore(){};

    public static ChatroomStore makeStore() {
        if (ONLY == null) {
            ONLY = new ChatroomStore();
        }
        return ONLY;
    }

    @Override
    public AChatroom getChatRoom(String chatroomName) {
        return null;
    }

    @Override
    public List<AChatroom> getAllPublicChatRooms() {
        return null;
    }

    @Override
    public AChatroom createChatRoom(String chatroomName, String type, String username, int size) {
        return null;
    }

    @Override
    public void addUserToChatroom(String chatroomName, String username) {

    }

    @Override
    public void removeUserFromChatroom(String chatroomName, String username) {

    }

    @Override
    public List<User> getUserList(String chatroomName) {
        return null;
    }

    @Override
    public List<AMessage> getMessageForUser(String username, String chatroomName) {
        return null;
    }

    @Override
    public String getAdmin(String chatroomName) {
        return null;
    }

    @Override
    public void setAdmin(String chatroomName, String username) {

    }
}
