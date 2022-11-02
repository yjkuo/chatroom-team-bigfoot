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
        AMessage message = MessageFactory.makeFactory().makeMessage(chatroom.getCurrentMessageID(), chatroomName, content, sender, receiver, type);
        chatroom.addMessage(message);

        if (content != null) {
            if (content.contains("hate speech")) {
                AUser senderObject = us.getUsers(sender);
                senderObject.setNumOfHateSpeech(senderObject.getNumOfHateSpeech() + 1);
                String warningContent = "Your speech contains hateful speech. If you tried to send it 3 times, you will be removed from all rooms.";
                sendMessage("", "System", sender, warningContent, chatroomName);
            } else {
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
                        Session userSession = us.getUserSession(receiver);
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

    @Override
    public void editMessage(int messageID, String chatroomName, String content) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);
        chatroom.editMessage(messageID, content);
        ArrayList<String> chatroomUsers = chatroom.getUsers();

        for (String chatroomUser: chatroomUsers) {
            try {
                Session userSession = us.getUserSession(chatroomUser);
                if (userSession != null) {
                    userSession.getRemote().sendString(gson.toJson("edit&" + String.valueOf(messageID) + "&" + content));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteMessage(int messageID, String chatroomName) {
        AChatroom chatroom = cs.getChatRoom(chatroomName);
        chatroom.deleteMessage(messageID);
        ArrayList<String> chatroomUsers = chatroom.getUsers();

        for (String chatroomUser: chatroomUsers) {
            try {
                Session userSession = us.getUserSession(chatroomUser);
                if (userSession != null) {
                    userSession.getRemote().sendString(gson.toJson("delete&" + String.valueOf(messageID)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
