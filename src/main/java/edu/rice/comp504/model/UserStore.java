package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.AUser;
import edu.rice.comp504.model.user.NullUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.user.UserFactory;
import org.eclipse.jetty.websocket.api.Session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore implements IUserStore{
    /**
     * List of all users.
     */
    private Map<String, AUser> userList = new ConcurrentHashMap<>();
    /**
     * List of users that has active connections with the server.
     */
    private Map<String, Session> userSessionMap = new ConcurrentHashMap<>();

    private Map<Session, String> sessionUserMap =  new ConcurrentHashMap<>();

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
    public AUser register(String username, String pwd, int age, String school, String[] interests) {
        AUser user = null;
        if (userList.get(username) == null) {
            user = UserFactory.makeFactory().makeUser(username, pwd, age, school, interests);
            userList.put(username, user);
        } else {
            user = new NullUser();
        }

        return user;
    }

    @Override
    public AUser login(String username, String pwd) {
        AUser user = userList.get(username);
        if ( user == null || !user.getPwd().equals(pwd)) {
            return new NullUser();
        }

        return user;
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
