package edu.rice.comp504;

import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class to maintain and organize the state of chat app.
 */
public interface ICentralOrganizer {
    Map<String, User> userList = new ConcurrentHashMap<>();
    Map<String, AChatroom> chatroomList = new ConcurrentHashMap<>();

    User register(String username, String pwd, int age, String school, String interests);

    User login(String username, String pwd);

    AChatroom getChatRoom(int chatroomID);

    AChatroom createChatRoom(String username, String chatroomName, String type, int size);

    AChatroom joinChatRoom(String username, String chatroomName);

    AChatroom inviteToJoin(String username, String chatroomName);

    ArrayList<String> getChatRoomUsers(String chatroomName);

    Map<String, User> getAllUsers();

    Map<String, AChatroom> getAllChatrooms();

    boolean deleteUser(String username, String chatroomName);

    void warnUser(String username, String chatroomName);

    boolean banUser(String username, String chatroomName);

    boolean bannedFromAll(String username);

    String getAdmin(String chatroomName);

    String setAdmin(String chatroomName, String username);

    ArrayList<String> getAllRooms();

    boolean sendMessage(String content, String type, String sender, String receiver, String chatroomName);

    List<AMessage> getMessageForUser(String username, String chatroomName);

    void deleteMessage(int messageID, String chatroomName);

    void editMessage(int messageID, String chatroomName, String content);

    void leaveRoom(String username, String chatroomName);
}
