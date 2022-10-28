package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.WebSocketAdapter;
import edu.rice.comp504.model.DispatcherAdapter;
import edu.rice.comp504.model.user.AUser;

import static spark.Spark.*;

/**
 * The chat app controller communicates with all the clients on the web socket.
 */
public class ChatAppController {

    /**
     * Chat App entry point.
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFiles.location("/public");
        webSocket("/chatapp", WebSocketAdapter.class);
        init();
        Gson gson = new Gson();
        DispatcherAdapter da = DispatcherAdapter.makeDispatcher();

        post("/register", (request, response) -> {
            AUser user = da.register(request.queryParams("username"),
                        request.queryParams("password"),
                        Integer.parseInt(request.queryParams("age")),
                        request.queryParams("school"),
                        request.queryParams("interests"));
            return gson.toJson(user);
        });

        post("/login", (request, response) -> {
            AUser user = da.login(request.queryParams("username"), request.queryParams("password"));
            return gson.toJson(user);
        });

        get("/chatroom/getChatroomList", (request, response) -> {
            da.getChatRoomForUser("username");
            return gson.toJson("get chatroom list");
        });

        post("/chatroom/createChatroom", (request, response) -> {
            da.createChatRoom("username", "chatroomName", "public", 5);
            return gson.toJson("create chatroom");
        });

        post("/chatroom/joinChatroom", (request, response) -> {
            da.joinChatRoom("username", "chatroomName");
            return gson.toJson("join chatroom");
        });

        post("/chatroom/connectToChatroom", (request, response) -> {
            da.getChatRoom("chatroomName");
            return gson.toJson("Connect to chat room");
        });

        post("/chatroom/leaveChatroom", (request, response) -> {
            da.leaveRoom("username", "chatroomName");
            return gson.toJson("leave chatroom");
        });

        post("/chatroom/inviteToChatroom", (request, response) -> {
            da.inviteToJoin("username", "chatroomName");
            return gson.toJson("Invite To chatroom");
        });

        get("/chatroom/userList", (request, response) -> {
            da.getAllUsers("chatroomName");
            return gson.toJson("user list");
        });

        get("/chatroom/getAdmin", (request, response) -> {
            da.getAdmin("chatroomName");
            return gson.toJson("get admin");
        });

        get("/chatroom/getMessages", (request, response) -> {
            da.getMessageForUser("username", "chatroomName");
            return gson.toJson("get messages");
        });

        post("/chatroom/sendMessage", (request, response) -> {
            da.sendMessage("content", "direct", "sender", "receiver", "chatroomName");
            return gson.toJson("send message");
        });

        post("/chatroom/editMessage", (request, response) -> {
            da.editMessage(1, "chatroomName", "new content");
            return gson.toJson("edit message");
        });

        post("/chatroom/deleteMessage", (request, response) -> {
            da.deleteMessage(1, "chatroomName");
            return gson.toJson("delete message");
        });

        post("/chatroom/warnUser", (request, response) -> {
            da.warnUser("username", "chatroomName");
            return gson.toJson("warn user");
        });

        post("/chatroom/removeUser", (request, response) -> {
            da.deleteUser("username", "chatroomName");
            return gson.toJson("remove user");
        });
    }

    /**
     * Get the heroku assigned port number.
     * @return The heroku assigned port number
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set.
    }
}
