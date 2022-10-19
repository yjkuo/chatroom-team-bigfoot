package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherAdapter implements IDispatcherAdapter {
    ChatroomStore cs ;
    MessageStore ms;
    UserStore us;

    private static DispatcherAdapter ONLY;

    private DispatcherAdapter() {
        cs = ChatroomStore.makeStore();
        ms = MessageStore.makeStore();
        us = UserStore.makeStore();
    }

    /**
     Get Central Organizer singleton.
     */
    public static DispatcherAdapter makeDispatcher() {
        if (ONLY == null) {
            ONLY = new DispatcherAdapter();
        }
        return ONLY;
    }

    @Override
    public User register(String username, String pwd, int age, String school, String interests) {
        return null;
    }

    @Override
    public User login(String username, String pwd) {
        return null;
    }

    @Override
    public void online(String username, Session userSession) {

    }

    @Override
    public void offline(String username, Session userSession) {

    }

    @Override
    public AChatroom getChatRoom(String chatroomName) {
        return null;
    }

    @Override
    public AChatroom createChatRoom(String username, String chatroomName, String type, int size) {
        return null;
    }

    @Override
    public AChatroom joinChatRoom(String username, String chatroomName) {
        return null;
    }

    @Override
    public AChatroom inviteToJoin(String username, String chatroomName) {
        return null;
    }

    @Override
    public ArrayList<AChatroom> getChatRoomForUser(String username) {
        return null;
    }

    @Override
    public List<String> getAllUsers(String chatroomName) {
        return null;
    }

    @Override
    public Map<String, AChatroom> getAllPublicChatRooms() {
        return null;
    }

    @Override
    public boolean deleteUser(String username, String chatroomName) {
        return false;
    }

    @Override
    public void warnUser(String username, String chatroomName) {

    }

    @Override
    public boolean banUser(String username, String chatroomName) {
        return false;
    }

    @Override
    public boolean bannedFromAll(String username) {
        return false;
    }

    @Override
    public String getAdmin(String chatroomName) {
        return null;
    }

    @Override
    public String setAdmin(String chatroomName, String username) {
        return null;
    }

    @Override
    public boolean sendMessage(String content, String type, String sender, String receiver, String chatroomName) {
        return false;
    }

    @Override
    public List<AMessage> getMessageForUser(String username, String chatroomName) {
        return null;
    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {

    }

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {

    }

    @Override
    public void leaveRoom(String username, String chatroomName) {

    }
}