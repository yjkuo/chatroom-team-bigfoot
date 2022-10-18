package edu.rice.comp504.model;

import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class to maintain and organize the state of chat app.
 */
public interface ICentralOrganizer {

    /**
     * Register the new user.
     * @param username String username
     * @param pwd String password
     * @param age Integer age
     * @param school String school name
     * @param interests String interests (combined)
     * @return
     */
    User register(String username, String pwd, int age, String school, String interests);

    /**
     * Log in an existing user.
     * @param username String username
     * @param pwd String password
     * @return
     */
    User login(String username, String pwd);

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
     * Return the chatroom with that specific name.
     * @param chatroomName
     * @return
     */
    AChatroom getChatRoom(String chatroomName);

    /**
     * Create the chatroom with specific name and size.
     * @param username String username
     * @param chatroomName String unique chatroomName
     * @param type String type
     * @param size Int size of the chatroom
     * @return
     */
    AChatroom createChatRoom(String username, String chatroomName, String type, int size);

    /**
     * Join the chatroom with unique chat room name.
     * @param username String username
     * @param chatroomName String chat room name
     * @return
     */
    AChatroom joinChatRoom(String username, String chatroomName);

    /**
     * Invite a user to join the chat room.
     * @param username String invited username
     * @param chatroomName String chat room name
     * @return
     */
    AChatroom inviteToJoin(String username, String chatroomName);

    /**
     * Return a list of users in the chat room.
     * @param username String username of the user
     * @return List of chat rooms of that user
     */
    ArrayList<AChatroom> getChatRoomForUser(String username);

    /**
     * Return a list of all users from the chatroom.
     * @return List of usernames.
     */
    List<String> getAllUsers(String chatroomName);

    /**
     * Return a list of all chat rooms.
     * @return List of chatrooms.
     */
    Map<String, AChatroom> getAllChatrooms();

    /**
     * Delete a user from the chat room.
     * @param username String username to be deleted
     * @param chatroomName String chat room name
     * @return Success or failure to delete the user
     */
    boolean deleteUser(String username, String chatroomName);

    /**
     * Warn the user of hate speech.
     * @param username String username to be warned.
     * @param chatroomName String chatroom name of the room in which message was sent.
     */
    void warnUser(String username, String chatroomName);

    /**
     * Ban the user from a specific room.
     * @param username String username to be banned
     * @param chatroomName String chatroom from which the user will be banned.
     * @return Success or failure to ban the user
     */
    boolean banUser(String username, String chatroomName);

    /**
     * Ban the user from all rooms.
     * @param username String username to be banned
     * @return Success or failure to delete the user
     */
    boolean bannedFromAll(String username);

    /**
     * Return the admin of the room.
     * @param chatroomName String chatroom to be checked.
     * @return username of the admin
     */
    String getAdmin(String chatroomName);

    /**
     * Set the admin of the room.
     * @param chatroomName String chatroom to be set.
     * @param username String username of the new admin.
     * @return username of the new admin.
     */
    String setAdmin(String chatroomName, String username);

    /**
     * Send the message to either everyone in the chat room or a specific user.
     * @param content String content of the message
     * @param type String type of the message (direct or normal)
     * @param sender String sender of the message
     * @param receiver String receiver of the message
     * @param chatroomName String chatroomName in which the message will be sent.
     * @return Success or failure to deliver the message
     */
    boolean sendMessage(String content, String type, String sender, String receiver, String chatroomName);

    /**
     * Get the messages based on specific user in a chat room.
     * @param username String username of the user.
     * @param chatroomName String chat room name.
     * @return
     */
    List<AMessage> getMessageForUser(String username, String chatroomName);

    /**
     * Delete the message from a chat room.
     * @param messageID Int message ID of message that will be deleted.
     * @param chatroomName String chat room name in which message resides.
     */
    void deleteMessage(int messageID, String chatroomName);

    /**
     * Edit the message from a chat room.
     * @param messageID Int message ID of message that will be edited.
     * @param chatroomName String chat room name in which message resides.
     * @param content String content of the new message
     */
    void editMessage(int messageID, String chatroomName, String content);

    /**
     * Leave a specific chat room.
     * @param username String username of the user that will leave the room
     * @param chatroomName String chat room name from which the user will leave
     */
    void leaveRoom(String username, String chatroomName);
}
