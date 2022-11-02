package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The interface of the chatroom store, which will manage the chatroom list and related actions.
 */
public interface IChatroomStore {
    /**
     * List of chat rooms on the server.
     */
    Map<String, AChatroom> chatroomList = new ConcurrentHashMap<>();

    /**
     * Get the chatroom by its name.
     * @param chatroomName Unique chatroom name
     * @return the chatroom
     */
    AChatroom getChatRoom(String chatroomName);

    /**
     * Get all public chat rooms on the server.
     * @return list of all public chatrooms.
     */
    List<AChatroom> getAllPublicChatRooms(String username);

    /**
     * Create the chatroom with specific name and size.
     * @param username String username
     * @param chatroomName String unique chatroomName
     * @param type String type
     * @param size Int size of the chatroom
     * @return the chatroom or null
     */
    AChatroom createChatRoom(String chatroomName, String type, String username, int size);

    /**
     * Add a user to the chatroom.
     * @param chatroomName unique chatroom name
     * @param username unique user name
     */
    AChatroom addUserToChatroom(String chatroomName, String username);

    /**
     * Remove a user from the chatroom.
     * @param chatroomName unique chatroom name
     * @param username unique user name
     */
    void removeUserFromChatroom(String chatroomName, String username);

    /**
     * Get the user list from a chatroom.
     * @param chatroomName unique chatroom name
     * @return list of users
     */
    List<String> getUserList(String chatroomName);

    /**
     * Get the message for a user in a chatroom.
     * @param username unique username
     * @param chatroomName unique chatroom name
     * @return list of messages for that specific user
     */
    List<AMessage> getMessageForUser(String username, String chatroomName);

    /**
     * Get the admin of the chatroom.
     * @param chatroomName unique chatroom name
     * @return username of the admin
     */
    String getAdmin(String chatroomName);

    /**
     * Set the admin of a chatroom.
     * @param chatroomName unique chatroom name
     * @param username username of the admin
     */
    void setAdmin(String chatroomName, String username);
}

