package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.message.DirectMessage;
import edu.rice.comp504.model.user.AUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
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
    private Map<Integer, AMessage> messages;
    private int currentMessageID;
    private Map<String, Integer> userJoinedTime;

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
        this.currentMessageID = 1;
        this.users = new ArrayList<>();
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
     * Get the next unique messageID
     * @return current MessageID
     */
    public int getCurrentMessageID() {
        return currentMessageID;
    }

    /**
     * Set the unique admin of chatroom.
     * @param admin the admin user.
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
        userJoinedTime.put(username, currentMessageID);
        numberOfUsers++;
    }

    /**
     * Remove a user from the chatroom.
     * @param username username of the user that will be removed
     */
    public void removeUser(String username) {
        users.remove(username);
        numberOfUsers--;
    }

    /**
     * Get the message for a specific user.
     * @param username username of the user
     * @return map of messages for that specific user
     */
    public ArrayList<AMessage> getMessages(String username) {
        ArrayList<AMessage> result = new ArrayList<>();
        int joinedTime = this.userJoinedTime.get(username);
        //check timestamp
        for (Map.Entry<Integer, AMessage> entry: this.messages.entrySet()) {
//            if (entry.getValue().getMessageID() >= joinedTime) {
//                //check whether direct message
//                if (Objects.equals(entry.getValue().getType(), "direct")) {
//                    if (Objects.equals(((DirectMessage) entry.getValue()).getReceiver(), username) ||
//                        Objects.equals(entry.getValue().getSender(), username)) {
//                        result.add(entry.getValue());
//                    }
//                } else {
//                    result.add(entry.getValue());
//                }
//            }
            if (entry.getValue().getMessageID() >= joinedTime
                    &&
                    (entry.getValue().getReceiver().equals("Everyone")
                    || entry.getValue().getReceiver().equals(username)
                    || entry.getValue().getSender().equals(username))) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * Add the message to the chatroom.
     */
    public void addMessage(AMessage message) {
        this.messages.put(message.getMessageID(), message);
        currentMessageID++;
    }

    /**
     * Delete the message from the chatroom.
     * @param messageID ID of the message that will be deleted
     */
    public void deleteMessage(int messageID) {
        this.messages.remove(messageID);
        currentMessageID--;
    }

    /**
     * Edit the message.
     * @param messageID ID of the message that will be edited
     * @param newContent new content of the message
     */
    public void editMessage(int messageID, String newContent) {
        AMessage messageToBeEdited = this.messages.get(messageID);
        if (messageToBeEdited != null) {
            messageToBeEdited.setContent(newContent);
            this.messages.replace(messageID, messageToBeEdited);
        }
    }
}
