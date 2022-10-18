package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.message.AMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AChatroom {
    private String roomName;
    private String type;
    private String admin;
    private int size;
    private ArrayList<String> users;
    private ArrayList<String> bans;
    private Map<Integer, AMessage> messages;
    private Map<String, Date> userJoinedTime;

    public AChatroom(String name, String type, int size) {
        this.roomName = name;
        this.type = type;
        this.size = size;
        this.users = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.messages = new ConcurrentHashMap<>();
        this.userJoinedTime = new ConcurrentHashMap<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public ArrayList<String> getBans() {
        return bans;
    }

    public void addBans(String username) {
        bans.add(username);
    }

    public void removeBans(String username) {
        if (users.contains(username)) {
            this.users.remove(username);
        }
    }

    public Map<Integer, AMessage> getMessages(String username) {
        //TODO filter message based on specific user's joined time
        return null;
    }

    public void addMessage() {
        //TODO coordinate with AMessage class to add the message
    }

    public void deleteMessage(int messageID) {
        //TODO coordinate with AMessage class to delete the message
    }

    public void editMessage(int messageID, String newContent) {
        //TODO coordinate with AMessage class to edit the message
    }
}
