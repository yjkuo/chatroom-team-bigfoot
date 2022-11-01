package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.AUser;
import edu.rice.comp504.model.user.IUserFactory;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The interface of the user store, which will manage the user list and related actions.
 */
public interface IUserStore {
    /**
     * List of all users.
     */
    Map<String, User> userList = new ConcurrentHashMap<>();
    /**
     * List of users that has active connections with the server.
     */
    Map<String, Session> userSessionMap = new ConcurrentHashMap<>();

    /**
     * Register the new user.
     * @param username String username
     * @param pwd String password
     * @param age Integer age
     * @param school String school name
     * @param interests String interests (combined)
     * @return the registered user or null
     */
    AUser register(String username, String pwd, int age, String school, String[] interests);

    /**
     * Log in an existing user.
     * @param username String username
     * @param pwd String password
     * @return the logged user or null
     */
    AUser login(String username, String pwd);

    /**
     * Return a list of users in the chat room.
     * @param username String username of the user
     * @return List of chat rooms of that user
     */
    ArrayList<String> getChatRoomForUser(String username);

    /**
     * Mark a user as online and store its unique session.
     * @param username String username
     * @param userSession Session userSession
     */
    void online(String username, Session userSession);

    /**
     * Mark a user as offline and remove its session from session map.
     * @param username String username
     * @param userSession Session userSession
     */
    void offline(String username, Session userSession);

    /**
     * Add a chatroom to user's chatroom list.
     * @param username unique username
     * @param chatroomName name of the chatroom to be added
     */
    void addChatRoomToList(String username, String chatroomName);

    /**
     * Remove a chatroom from user's chatroom list.
     * @param username unique username
     * @param chatroomName name of the chatroom to be removed
     */
    void removeChatRoomFromList(String username, String chatroomName);

    /**
     * Invite user to join the chatroom.
     * @param username unique username
     * @param chatroomName name of the chatroom that invite the user
     */
    void invitedToJoin(String chatroomName, String username);

    /**
     * Return a list of all users.
     * @return list of all users
     */
    List<User> getAllUsers();

    /**
     * Warn the user of hateful speech.
     * @param username username of warned user
     */
    void warnUser(String username);

    /**
     * Ban the user from all chatrooms.
     * @param username username of banned user
     * @return the success or failure to perform this operation
     */
    boolean banUserFromAll(String username);
}
