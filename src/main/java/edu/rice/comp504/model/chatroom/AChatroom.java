package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.message.AMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents the chatrooms within the app.
 */
public abstract class AChatroom {
    private String roomName;
    private String type;
    private String admin;
    private int size;
    private int numberOfUsers;
    private ArrayList<String> users;
    private ArrayList<String> bans;
    private Map<Integer, AMessage> messages;
    private Map<String, Date> userJoinedTime;

    /**
     * Constructor for AChatroom.
     * @param name Unique name of chat room.
     * @param type Type of chat room (private vs. public)
     * @param size Maximum size of the chat room.
     */
    public AChatroom(String name, String type, int size) {
        this.roomName = name;
        this.type = type;
        this.size = size;
        this.numberOfUsers = 0;
        this.users = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.messages = new ConcurrentHashMap<>();
        this.userJoinedTime = new ConcurrentHashMap<>();
    }

    /**
     * Get the unique name of chatroom.
     * @return Name of chatroom
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Get the size of chatroom.
     * @return size of chatroom
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the type of chatroom.
     * @return type of chatroom
     */
    public String getType() {
        return type;
    }

    /**
     * Get the unique admin of chatroom.
     * @return admin of chatroom
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * Set the unique admin of chatroom.
     * @param admin
     */
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    /**
     * Return current number of users in the room. When this number becomes 0, the room will be destroyed.
     * @return number of users.
     */
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    /**
     * Set the number of users in the chatroom.
     * @param numberOfUsers number of users that will be in the chat room.
     */
    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    /**
     * Get the list of users in the chatroom.
     * @return A list of users.
     */
    public ArrayList<String> getUsers() {
        return users;
    }

    /**
     * Add a user to the chatroom.
     * @param username username of the user that will be added
     */
    public void addUser(String username) {
        users.add(username);
    }

    /**
     * Remove a user from the chatroom.
     * @param username username of the user that will be removed
     */
    public void removeUser(String username) {
        users.remove(username);
    }

    /**
     * Get the list of banned users in the chatroom.
     * @return list of banned users
     */
    public ArrayList<String> getBans() {
        return bans;
    }

    /**
     * Add a user to the banned user list.
     * @param username username of the user that will be banned
     */
    public void addBans(String username) {
        bans.add(username);
    }

    /**
     * Remove a user from the ban list.
     * @param username username of the user that will be unbanned
     */
    public void removeBans(String username) {
        if (users.contains(username)) {
            this.users.remove(username);
        }
    }

    /**
     * Get the message for a specific user.
     * @param username username of the user
     * @return map of messages for that specific user
     */
    public Map<Integer, AMessage> getMessages(String username) {
        //TODO filter message based on specific user's joined time
        return null;
    }

    /**
     * Add the message to the chatroom.
     */
    public void addMessage() {
        //TODO coordinate with AMessage class to add the message
    }

    /**
     * Delete the message from the chatroom.
     * @param messageID ID of the message that will be deleted
     */
    public void deleteMessage(int messageID) {
        //TODO coordinate with AMessage class to delete the message
    }

    /**
     * Edit the message.
     * @param messageID ID of the message that will be edited
     * @param newContent new content of the message
     */
    public void editMessage(int messageID, String newContent) {
        //TODO coordinate with AMessage class to edit the message
    }
}
