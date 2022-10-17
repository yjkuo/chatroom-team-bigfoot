package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.message.AMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AChatroom {
    private String roomName;
    private int id;
    private String type;
    private String admin;
    private ArrayList<String> users;
    private ArrayList<String> bans;
    private Map<Integer, AMessage> messages;

    public AChatroom(int id, String name, String type) {
        this.roomName = name;
        this.id = id;
        this.type = type;
        this.users = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.messages = new ConcurrentHashMap<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getId() {
        return id;
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

    public Map<Integer, AMessage> getMessages() {
        return messages;
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
