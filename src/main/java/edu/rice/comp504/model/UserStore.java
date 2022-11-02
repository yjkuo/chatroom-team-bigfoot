package edu.rice.comp504.model;

import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.user.AUser;
import edu.rice.comp504.model.user.NullUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.user.UserFactory;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
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
    public ArrayList<String> getChatRoomForUser(String username) {
        AUser user = userList.get(username);
        if ( user != null ) {
            return user.getMyChatRooms();
        }
        return null;
    }

    @Override
    public void online(String username, Session userSession) {
        userSessionMap.put(username, userSession);
        sessionUserMap.put(userSession, username);
    }

    @Override
    public void offline(String username, Session userSession) {
        if (username != null) {
            String user = sessionUserMap.get(userSession);
            sessionUserMap.remove(userSession);
            userSessionMap.remove(user);
        }
    }

    @Override
    public void addChatRoomToList(String username, String chatroomName) {
        AUser user = userList.get(username);
        if (user != null) {
            user.addChatRoom(chatroomName);
        }
    }

    @Override
    public void removeChatRoomFromList(String username, String chatroomName) {
        AUser user = userList.get(username);
        if (user != null) {
            user.removeChatRoom(chatroomName);
        }
    }

    private void sendInviteToWebSocket(String receiver){
        try {
            Session userSession = getUserSession(receiver);
            if (userSession != null) {
                userSession.getRemote().sendString("updateInvites");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean invitedToJoin(String chatroomName, String sender, String receiver) {
        AUser senderUser = userList.get(sender);
        AUser receiverUser = userList.get(receiver);

        if (senderUser == null  || receiverUser == null) {
            return false;
        }

        if (receiverUser.getMyChatRooms().contains(chatroomName) || receiverUser.getInvitedRooms().contains(chatroomName)) {
            return true;
        }

        if (senderUser.getSchool().equals(receiverUser.getSchool())) {
            receiverUser.addRoomToInvitedList(chatroomName);
            sendInviteToWebSocket(receiver);
            return true;
        }

        String[] senderInterests = senderUser.getInterests();
        String[] receiverInterests = receiverUser.getInterests();
        for (int i = 0; i < senderInterests.length; i++) {
            for (int j = 0; j < receiverInterests.length; j++) {
                if (senderInterests[i].equals(receiverInterests[j])) {
                    receiverUser.addRoomToInvitedList(chatroomName);
                    sendInviteToWebSocket(receiver);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public AUser getUsers(String username) {
        return userList.get(username);
    }

    public void setUsersOpenChatroom(String username, String chatroomName) {
        AUser user = userList.get(username);
        if (user != null) {
            user.setOpenChatroom(chatroomName);
        }
    }

    public ArrayList<String> getInvitedRoomForUser(String username) {
        return userList.get(username).getInvitedRooms();
    }

    public Session getUserSession(String username) {
        return userSessionMap.get(username);
    }

    public void promptUsersToUpdatePublicRooms() {
        for (Map.Entry<String, AUser> entry : userList.entrySet()) {
            String chatroomUser = entry.getKey();
            try {
                Session userSession = getUserSession(chatroomUser);
                if (userSession != null) {
                    userSession.getRemote().sendString("updatePublicRooms");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
