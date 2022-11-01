package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.AUser;
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
        String[] interests = {"Music"};
        AUser firstUser = us.register("Sky", "a", 12, "Rice University", interests);
        createChatRoom("Sky", "Owl Games", "public", 20);
        createChatRoom("Sky", "Secret Service", "private", 10);
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
    public AUser register(String username, String pwd, int age, String school, String interests) {
         return us.register(username, pwd, age, school, interests.split("&"));
    }

    @Override
    public AUser login(String username, String pwd) {
        return us.login(username, pwd);
    }

    @Override
    public void online(String username, Session userSession) {
        us.online(username, userSession);
    }

    @Override
    public void offline(String username, Session userSession) {
        us.offline(username, userSession);
    }

    @Override
    public AChatroom getChatRoom(String chatroomName) {
        return cs.getChatRoom(chatroomName);
    }

    @Override
    public AChatroom createChatRoom(String username, String chatroomName, String type, int size) {
        AChatroom chatroom = cs.createChatRoom(chatroomName, type, username, size);
        if (!chatroom.getRoomName().equals("")) {
            us.addChatRoomToList(username, chatroomName);
        }
        return chatroom;
    }

    @Override
    public AChatroom joinChatRoom(String username, String chatroomName) {
        AChatroom result = cs.addUserToChatroom(chatroomName, username);
        us.addChatRoomToList(username, chatroomName);
        return result;
    }

    @Override
    public AChatroom inviteToJoin(String username, String chatroomName) {
        return null;
    }

    @Override
    public ArrayList<String> getChatRoomForUser(String username) {
        return us.getChatRoomForUser(username);
    }

    @Override
    public List<String> getAllUsers(String chatroomName) {
        return cs.getUserList(chatroomName);
    }

    @Override
    public ArrayList<AChatroom> getAllPublicChatRooms() {
        return cs.getAllPublicChatRooms();
    }

    @Override
    public boolean deleteUser(String username, String chatroomName) {
        cs.removeUserFromChatroom(chatroomName, username);
        us.removeChatRoomFromList(username, chatroomName);
        return true;
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
        return cs.getAdmin(chatroomName);
    }

    @Override
    public void setAdmin(String chatroomName, String username) {
        cs.setAdmin(chatroomName, username);
    }

    @Override
    public boolean sendMessage(String content, String type, String sender, String receiver, String chatroomName) {
        return false;
    }

    @Override
    public List<AMessage> getMessageForUser(String username, String chatroomName) {
        return cs.getMessageForUser(username, chatroomName);
    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {

    }

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {

    }

    @Override
    public void leaveRoom(String username, String chatroomName) {
        cs.removeUserFromChatroom(chatroomName, username);
        us.removeChatRoomFromList(username, chatroomName);
    }
}
