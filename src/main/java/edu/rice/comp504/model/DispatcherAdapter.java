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
        AUser secondUser = us.register("Ray", "a", 18, "Rice University", interests);
        AUser thirdUser = us.register("Godzilla", "a", 28, "Rice University", interests);
        createChatRoom("Sky", "Owl Games", "public", 20);
        createChatRoom("Ray", "Ray rays", "public", 3);
        createChatRoom("Ray", "Ray private", "private", 1);
        createChatRoom("Sky", "Secret Service", "private", 10);
        createChatRoom("Godzilla", "Attack plan", "public", 10);
        createChatRoom("Godzilla", "Size 1", "public", 1);
        joinChatRoom("Ray", "Owl Games");
        joinChatRoom("Godzilla", "Owl Games");

        sendMessage("Hello Everyone^^^", "", "Sky", "Everyone", "Owl Games");
        sendMessage("Good to meet you all", "", "Sky", "Everyone", "Owl Games");
        sendMessage("Hello", "", "Ray", "Everyone", "Owl Games");
        sendMessage("Hello Sky", "", "Godzilla", "Sky", "Owl Games");
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

    public AChatroom openChatRoom(String username, String chatroomName) {
        us.setUsersOpenChatroom(username, chatroomName);
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
        if (!result.getRoomName().equals("")) {
            us.addChatRoomToList(username, chatroomName);
        }
        return result;
    }

    @Override
    public boolean inviteToJoin(String sender, String receiver, String chatroomName) {
        return us.invitedToJoin(chatroomName, sender, receiver);
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
    public ArrayList<String> getAllPublicChatRooms(String username) {
        return cs.getAllPublicChatRooms(username);
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
        ms.sendMessage(type, sender, receiver, content, chatroomName);
        return true;
    }

    @Override
    public List<AMessage> getMessageForUser(String username, String chatroomName) {
        return cs.getMessageForUser(username, chatroomName);
    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {
        ms.deleteMessage(messageID, chatroomName);
    }

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {
        ms.editMessage(messageID, chatroomName, content);
    }

    @Override
    public void leaveRoom(String username, String chatroomName) {
        cs.removeUserFromChatroom(chatroomName, username);
        us.removeChatRoomFromList(username, chatroomName);
    }

    public ArrayList<String> getInvitedRoomForUser(String username) {
        return us.getInvitedRoomForUser(username);
    }
}
