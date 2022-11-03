package edu.rice.comp504.model;

import com.google.gson.Gson;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.chatroom.NullChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.message.MessageFactory;
import edu.rice.comp504.model.user.AUser;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherAdapter implements IDispatcherAdapter {
    private ChatroomStore cs ;
    private MessageStore ms;
    private UserStore us;
    private Gson gson;

    private static DispatcherAdapter ONLY;

    private DispatcherAdapter() {
        cs = ChatroomStore.makeStore();
        ms = MessageStore.makeStore();
        us = UserStore.makeStore();
        gson = new Gson();
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
        if (us.getUsers(username).getStatus() == 2) {
            return new NullChatroom();
        } else {
            AChatroom chatroom = cs.createChatRoom(chatroomName, type, username, size);
            if (!chatroom.getRoomName().equals("")) {
                us.addChatRoomToList(username, chatroomName);
                us.promptUsersToUpdatePublicRooms();
            }
            return chatroom;
        }
    }

    @Override
    public AChatroom joinChatRoom(String username, String chatroomName) {
        AChatroom result = cs.addUserToChatroom(chatroomName, username);
        if (!result.getRoomName().equals("")) {
            us.addChatRoomToList(username, chatroomName);
            ms.promptUsersToUpdateUserList(chatroomName);
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
        return cs.getAllPublicChatRooms(username, us.getUsers(username).getStatus(), us.getUsers(username).getBannedRooms());
    }

    @Override
    public boolean banUser(String username, String chatroomName) {
        this.leaveRoom(username, chatroomName, 1);
        us.getUsers(username).addBannedRooms(chatroomName);
        ms.promptUserTheyAreBanned(username, chatroomName);
        return true;
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
    public void leaveRoom(String username, String chatroomName, int reason) {
        String msg = null;
        AUser user = us.getUsers(username);
        switch (reason) {
            case 0 : // voluntarily left
                msg = "user " + username + " leaves room voluntarily.";
                break;
            case 1: // being baned
                msg = "user " + username + " leaves room because of being banned.";
                break;
            default:
                break;
        }
        cs.removeUserFromChatroom(chatroomName, username);
        us.removeChatRoomFromList(username, chatroomName);

        if (cs.getChatRoom(chatroomName).getNumberOfUsers() == 0) {
            cs.removeChatRoom(chatroomName);
        } else {
            if (Objects.equals(cs.getAdmin(chatroomName), username)) {
                cs.setAdmin(chatroomName, cs.getUserList(chatroomName).get(0));
            }
            ms.sendMessage("", "System", "Everyone", msg, chatroomName);
            ms.promptUsersToUpdateUserList(chatroomName);
        }
    }

    public void leaveAllRoom(String username) {
        List<String> myChatRooms = new ArrayList<>(us.getUsers(username).getMyChatRooms());
        for (String room: myChatRooms) {
            this.leaveRoom(username, room, 0);
        }
    }

    public void banAll(String username) {
        ArrayList<String> myChatRooms = us.getChatRoomForUser(username);
        AUser userToBeBanned = us.getUsers(username);
        for (Map.Entry<String, AChatroom> chatroom: cs.getAllChatrooms().entrySet()) {
            for (String roomname: myChatRooms) {
                if (Objects.equals(roomname, chatroom.getValue().getRoomName())) {
                    String warningContent = "You have been banned from all rooms.";
                    ms.sendMessage("", "System", username, warningContent, chatroom.getKey());
                    this.leaveRoom(username, roomname, 1);
                    break;
                }
            }
        }
        for (String chatroom: cs.getAllChatrooms().keySet()) {
            userToBeBanned.addBannedRooms(chatroom);
        }
        ms.promptUserTheyArePermanentlyBanned(username);
    }
    
    public void reportUser(String username, String chatroomName) {
        String msg = username + " was reported by a member in this chatroom.";
        ms.sendMessage("", "System", "Everyone", msg, chatroomName);
    }

    public ArrayList<String> getInvitedRoomForUser(String username) {
        return us.getInvitedRoomForUser(username);
    }
}
