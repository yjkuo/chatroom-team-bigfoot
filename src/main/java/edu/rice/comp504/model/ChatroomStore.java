package edu.rice.comp504.model;

import edu.rice.comp504.controller.ChatAppController;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.chatroom.ChatroomFactory;
import edu.rice.comp504.model.chatroom.NullChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.AUser;
import edu.rice.comp504.model.user.NullUser;
import edu.rice.comp504.model.user.User;
import edu.rice.comp504.model.user.UserFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ChatroomStore implements IChatroomStore{
    /**
     * List of chat rooms on the server.
     */
    private Map<String, AChatroom> chatroomList = new ConcurrentHashMap<>();
    private static ChatroomStore ONLY;

    private ChatroomStore(){}

    /**
     * Create a chatroom singleton store.
     * @return the chatroom singleton store
     */
    public static ChatroomStore makeStore() {
        if (ONLY == null) {
            ONLY = new ChatroomStore();
        }
        return ONLY;
    }

    @Override
    public AChatroom getChatRoom(String chatroomName) {
        return chatroomList.get(chatroomName);
    }

    @Override
    public ArrayList<String> getAllPublicChatRooms(String username) {
        ArrayList<String> publicChatrooms = new ArrayList<>();
        for (Map.Entry<String, AChatroom> entry : chatroomList.entrySet()) {
            AChatroom chatroom = entry.getValue();
            boolean userAlreadyJoined = false;
            for (String user: chatroom.getUsers()) {
                if (Objects.equals(user, username)) {
                    userAlreadyJoined = true;
                    break;
                }
            }
            if (chatroom.getType().equals("public") && !userAlreadyJoined) {
                publicChatrooms.add(chatroom.getRoomName());
            }
        }
        return publicChatrooms;
    }

    @Override
    public AChatroom createChatRoom(String chatroomName, String type, String username, int size) {
        AChatroom chatroom = null;
        if (chatroomList.get(chatroomName) == null) {
            chatroom = ChatroomFactory.makeFactory().makeChatRoom(chatroomName, type, size);
            chatroomList.put(chatroomName, chatroom);
            addUserToChatroom(chatroomName, username);
            setAdmin(chatroomName, username);
        } else {
            chatroom = new NullChatroom();
        }
        return chatroom;
    }

    public void removeChatRoom(String chatroomName) {
        chatroomList.remove(chatroomName);
    }

    @Override
    public AChatroom addUserToChatroom(String chatroomName, String username) {
        AChatroom chatroom = chatroomList.get(chatroomName);
        if (chatroom != null) {
            if (chatroom.getNumberOfUsers() < chatroom.getSize()) {
                chatroom.addUser(username);
            }
            else {
                return new NullChatroom();
            }
        }
        return chatroom;
    }

    @Override
    public void removeUserFromChatroom(String chatroomName, String username) {
        AChatroom roomToRemove = this.chatroomList.get(chatroomName);
        roomToRemove.removeUser(username);
    }

    @Override
    public List<String> getUserList(String chatroomName) {
        return chatroomList.get(chatroomName).getUsers();
    }

    @Override
    public List<AMessage> getMessageForUser(String username, String chatroomName) {
        return chatroomList.get(chatroomName).getMessages(username);
    }

    @Override
    public String getAdmin(String chatroomName) {
        return chatroomList.get(chatroomName).getAdmin();
    }

    @Override
    public void setAdmin(String chatroomName, String username) {
        AChatroom chatroom = chatroomList.get(chatroomName);
        if (chatroom != null) {
            chatroom.setAdmin(username);
        }
    }

    public Map<String, AChatroom> getAllChatrooms() {
        return chatroomList;
    }

}
