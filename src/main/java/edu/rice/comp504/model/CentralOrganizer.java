package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CentralOrganizer implements ICentralOrganizer{
    private Map<String, User> userList;
    private Map<String, Session> userSessionMap;
    private Map<String, AChatroom> chatroomList;

    private static CentralOrganizer ONLY;

    private CentralOrganizer() {
        userList = new ConcurrentHashMap<>();
        chatroomList = new ConcurrentHashMap<>();
        userSessionMap = new ConcurrentHashMap<>();
    }

    public static CentralOrganizer makeOrganizer() {
        if (ONLY == null) {
            ONLY = new CentralOrganizer();
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
    public AChatroom getChatRoom(int chatroomID) {
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
    public ArrayList<String> getChatRoomUsers(String chatroomName) {
        return null;
    }

    @Override
    public Map<String, User> getAllUsers() {
        return null;
    }

    @Override
    public Map<String, AChatroom> getAllChatrooms() {
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
    public ArrayList<String> getAllRooms() {
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
