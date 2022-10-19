package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.User;
import org.eclipse.jetty.websocket.api.Session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore implements IUserStore{
    /**
     * List of all users.
     */
    private Map<String, User> userList = new ConcurrentHashMap<>();
    /**
     * List of users that has active connections with the server.
     */
    private Map<String, Session> userSessionMap = new ConcurrentHashMap<>();

    private static UserStore ONLY;

    private UserStore(){}

    /**
     * Create the singleton user store.
     * @return user singleton store
     */
    public static UserStore makeStore() {
        if (ONLY == null) {
            ONLY = new UserStore();
        }
        return ONLY;
    }

    @Override
    public User register(String username, String pwd, int age, String school, String[] interests) {
        return null;
    }

    @Override
    public User login(String username, String pwd) {
        return null;
    }

    @Override
    public List<AChatroom> getChatRoomForUser(String username) {
        return null;
    }

    @Override
    public void online(String username, Session userSession) {

    }

    @Override
    public void offline(String username, Session userSession) {

    }

    @Override
    public void addChatRoomToList(String username, String chatroomName) {

    }

    @Override
    public void removeChatRoomFromList(String username, String chatroomName) {

    }

    @Override
    public void invitedToJoin(String chatroomName, String username) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void warnUser(String username) {

    }

    @Override
    public boolean banUserFromAll(String username) {
        return false;
    }
}
