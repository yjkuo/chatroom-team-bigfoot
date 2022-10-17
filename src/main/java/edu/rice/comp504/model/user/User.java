package edu.rice.comp504.model.user;

import java.util.ArrayList;

public class User {
    private String username;
    private String pwd;
    private int age;
    private String school;
    private String[] interests;
    //TODO: Should we identify chat room through id or name?
    private ArrayList<Integer> myChatRooms;
    //Indicate the status of user (offline - 0, online - 1, banned - 2)
    private int status;

    public User(String username, String pwd, int age, String school, String[] interests) {
        this.username = username;
        this.pwd = pwd;
        this.age = age;
        this.school = school;
        this.interests = interests;
        this.myChatRooms = new ArrayList<>();
        this.status = 0;
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

    public void addChatRoom(int chatRoomID) {
        myChatRooms.add(chatRoomID);
    }

    public ArrayList<Integer> getMyChatRooms() {
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
