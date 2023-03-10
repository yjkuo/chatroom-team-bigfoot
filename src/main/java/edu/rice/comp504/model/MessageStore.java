package edu.rice.comp504.model;

import com.google.gson.Gson;
import edu.rice.comp504.model.chatroom.AChatroom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.message.MessageFactory;
import edu.rice.comp504.model.user.AUser;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;

public class MessageStore implements IMessageStore{
    private static MessageStore ONLY;

    private ChatroomStore cs ;
    private UserStore us;
    private Gson gson;

    private MessageStore() {
        cs = ChatroomStore.makeStore();
        us = UserStore.makeStore();
        gson = new Gson();
    }

    /**
     * Create the message singleton store.
     * @return message singleton store
     */
    public static MessageStore makeStore() {
        if (ONLY == null) {
            ONLY = new MessageStore();
        }
        return ONLY;
    }

    @Override
    public void sendMessage(String type, String sender, String receiver, String content, String chatroomName) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);

        if (content != null) {
            if (content.contains("hate speech")) {
                AUser senderObject = us.getUsers(sender);
                senderObject.setNumOfHateSpeech(senderObject.getNumOfHateSpeech() + 1);
                int currentHateSpeechNum = senderObject.getNumOfHateSpeech();
                String warningContent = "Your speech contains hateful speech. If you tried to send it 3 times, you will be removed from all rooms." +
                        " You have sent " + currentHateSpeechNum + " hateful speeches.";
                if (currentHateSpeechNum < 3) {
                    sendMessage("", "System", sender, warningContent, chatroomName);
                }
            } else {
                AMessage message = MessageFactory.makeFactory().makeMessage(chatroom.getCurrentMessageID(), chatroomName, content, sender, receiver, type);
                chatroom.addMessage(message);
                if (receiver.equals("Everyone")) {
                    ArrayList<String> chatroomUsers = chatroom.getUsers();
                    for (String chatroomUser: chatroomUsers) {
//                if (chatroomUser.equals(sender)) {
//                    continue;
//                }
                        try {
                            Session userSession = us.getUserSession(chatroomUser);
                            if (userSession != null) {
                                userSession.getRemote().sendString(gson.toJson(message));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        Session userSession = us.getUserSession(sender);
                        if (userSession != null) {
                            userSession.getRemote().sendString(gson.toJson(message));
                        }
                        userSession = us.getUserSession(receiver);
                        if (userSession != null) {
                            userSession.getRemote().sendString(gson.toJson(message));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendStringToAllInChatroom(String content, String chatroomName) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);
        ArrayList<String> chatroomUsers = chatroom.getUsers();
        for (String chatroomUser: chatroomUsers) {
            try {
                Session userSession = us.getUserSession(chatroomUser);
                if (userSession != null) {
                    userSession.getRemote().sendString(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void promptUsersToUpdateUserList(String chatroomName) {
        sendStringToAllInChatroom("updateUsers", chatroomName);
    }

    public void promptUsersToUpdateMessages(String chatroomName) {
        sendStringToAllInChatroom("updateMessages", chatroomName);
    }

    /**
     * Tell users frontend that they are banned from the chatroom.
     */
    public void promptUserTheyAreBanned(String user, String chatroomName) {
        String string = String.format("ban&%s", chatroomName);
        try {
            Session userSession = us.getUserSession(user);
            if (userSession != null) {
                userSession.getRemote().sendString(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tell users frontend that they have been permanently banned.
     */
    public void promptUserTheyArePermanentlyBanned(String user) {
        try {
            Session userSession = us.getUserSession(user);
            if (userSession != null) {
                userSession.getRemote().sendString("Restricted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);
        chatroom.editMessage(messageID, content);
        promptUsersToUpdateMessages(chatroomName);
    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);
        chatroom.deleteMessage(messageID);
        promptUsersToUpdateMessages(chatroomName);
    }
}
