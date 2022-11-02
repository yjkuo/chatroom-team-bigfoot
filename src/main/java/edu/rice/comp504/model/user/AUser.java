package edu.rice.comp504.model.user;

import edu.rice.comp504.model.ChatroomStore;
import edu.rice.comp504.model.DispatcherAdapter;
import edu.rice.comp504.model.chatroom.AChatroom;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a single user within the app.
 */
public abstract class AUser {
    private String username;
    private String pwd;
    private int age;
    private String school;
    private String[] interests;
    private ArrayList<String> myChatRooms;
    private ArrayList<String> invitedRooms;
    //Indicate the status of user (normal - 0, warned - 1, banned - 2)
    private int status;
    private boolean online;
    private int numOfHateSpeech;
    private String openChatroom;

    /**
     * Constructor.
     * @param username unique username
     * @param pwd password of user
     * @param age age of user
     * @param school school of user
     * @param interests list of interests of user
     */
    public AUser(String username, String pwd, int age, String school, String[] interests) {
        this.username = username;
        this.pwd = pwd;
        this.age = age;
        this.school = school;
        this.interests = interests;
        this.myChatRooms = new ArrayList<>();
        this.invitedRooms = new ArrayList<>();
        this.status = 0;
        this.numOfHateSpeech = 0;
        this.online = false;
    }

    /**
     * Return the unique username of the user.
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Return the password of the user.
     * @return password
     */
    public String getPwd() {
        return this.pwd;
    }

    /**
     * Return the age of the user.
     * @return age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Return the school of the user.
     * @return school name
     */
    public String getSchool() {
        return this.school;
    }

    /**
     * Return the list of interests.
     * @return list of interests
     */
    public String[] getInterests() {
        return this.interests;
    }

    /**
     * Add a chatroom to user's chatroom list.
     * @param chatroomName chatroom to be added.
     */
    public void addChatRoom(String chatroomName) {
        if (invitedRooms.contains(chatroomName)) {
            invitedRooms.remove(chatroomName);
        }
        myChatRooms.add(chatroomName);
    }

    /**
     * Get the list of chatrooms of the user.
     * @return list of chatrooms
     */
    public ArrayList<String> getMyChatRooms() {
        return this.myChatRooms;
    }

    /**
     * Remove a chatroom from user's chatroom list.
     * @param chatroomName unique name of chatroom that will be removed
     */
    public void removeChatRoom(String chatroomName) {
        myChatRooms.remove(chatroomName);
    }

    /**
     * Get status of the user (normal, warned, banned).
     * @return status of the user
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set status of the user (normal, warned, banned).
     * @param status status that will be set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Get which chatroom user has open.
     * @return open chatroom
     */
    public String getOpenChatroom() {
        return openChatroom;
    }

    /**
     * Set which chatroom user has open.
     * @param chatroom opened chatroom
     */
    public void setOpenChatroom(String chatroom) {
        this.openChatroom = chatroom;
    }

    /**
     * Get the number of hate speech. (User will be banned from all rooms if this number reaches 3).
     * @return number of hate speech made
     */
    public int getNumOfHateSpeech() {
        return numOfHateSpeech;
    }

    /**
     * Update the number of hate speeches.
     * @param numOfHateSpeech new number of hate speeches made.
     */
    public void setNumOfHateSpeech(int numOfHateSpeech) {
        this.numOfHateSpeech = numOfHateSpeech;
        if (numOfHateSpeech == 3) {
            DispatcherAdapter.makeDispatcher().banAll(this.username);
            this.setStatus(2);
        }
    }

    /**
     * Whether the user is online (i.e. has an active session that connects to the server).
     * @return online or offline
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Toggle the online status of the user.
     * @param online whether user is online or not
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Get the list of invited rooms
     */
    public ArrayList<String> getInvitedRooms() {
        return invitedRooms;
    }

    /**
     * Add to the list of invited rooms
     */
    public ArrayList<String> addRoomToInvitedList(String chatroomName) {
        invitedRooms.add(chatroomName);
        return invitedRooms;
    }
}
