package edu.rice.comp504.model.user;

import java.util.ArrayList;

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
    private int numOfHateSpeech;

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
    }

    public String getUsername() {
        return this.username;
    }

    public String getPwd() {
        return this.pwd;
    }

    public int getAge() {
        return this.age;
    }

    public String getSchool() {
        return this.school;
    }

    public String[] getInterests() {
        return this.interests;
    }

    public void addChatRoom(String chatroomName) {
        myChatRooms.add(chatroomName);
    }

    public ArrayList<String> getMyChatRooms() {
        return this.myChatRooms;
    }

    public void removeChatRoom(int chatRoomID) {
        myChatRooms.remove(chatRoomID);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
