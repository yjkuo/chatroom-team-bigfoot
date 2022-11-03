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
            return gson.toJson(da.getChatRoomForUser(request.queryParams("username")));
        });

        get("/chatroom/getInvitedRoomList", (request, response) -> {
            return gson.toJson(da.getInvitedRoomForUser(request.queryParams("username")));
        });

        get("/chatroom/getPublicRoomList", (request, response) -> {
            return gson.toJson(da.getAllPublicChatRooms(request.queryParams("username")));
        });

        post("/chatroom/createChatroom", (request, response) -> {
            return gson.toJson(da.createChatRoom(request.queryParams("username"),
                    request.queryParams("chatroomName"),
                    request.queryParams("type"),
                    Integer.parseInt(request.queryParams("size"))
            ));
        });

        post("/chatroom/joinChatroom", (request, response) -> {
            return gson.toJson(da.joinChatRoom(request.queryParams("username"), request.queryParams("chatroomName")));
        });

        post("/chatroom/connectToChatroom", (request, response) -> {
            return gson.toJson(da.openChatRoom(request.queryParams("username"), request.queryParams("chatroomName")));
        });

        post("/chatroom/leaveChatroom", (request, response) -> {
            da.leaveRoom(request.queryParams("username"), request.queryParams("chatroomName"), 0);
            return gson.toJson("leave chatroom");
        });

        post("/chatroom/leaveAllChatroom", (request, response) -> {
            da.leaveAllRoom(request.queryParams("username"));
            return gson.toJson("leave chatroom");
        });

        post("/chatroom/report", ((request, response) -> {
            da.reportUser(request.queryParams("user"), request.queryParams("chatroomName"));
            return gson.toJson("reported user");
        }));

        post("/chatroom/inviteToChatroom", (request, response) -> {
            return gson.toJson(da.inviteToJoin(request.queryParams("sender"), request.queryParams("receiver"), request.queryParams("chatroomName")));
        });

        post("/chatroom/userList", (request, response) -> {
            return gson.toJson(da.getAllUsers(request.queryParams("chatroomName")));
        });

        get("/chatroom/getAdmin", (request, response) -> {
            da.getAdmin("chatroomName");
            return gson.toJson("get admin");
        });

        get("/chatroom/getMessages", (request, response) -> {
            return gson.toJson(da.getMessageForUser(request.queryParams("username"), request.queryParams("chatroomName")));
        });

        post("/chatroom/sendMessage", (request, response) -> {
            da.sendMessage(request.queryParams("content"), "", request.queryParams("sender"), request.queryParams("receiver"), request.queryParams("chatroomName"));
            return gson.toJson("send message");
        });

        post("/chatroom/editMessage", (request, response) -> {
            da.editMessage(Integer.parseInt(request.queryParams("id")), request.queryParams("chatroomName"), request.queryParams("content"));
            return gson.toJson("edit message");
        });

        post("/chatroom/deleteMessage", (request, response) -> {
            da.deleteMessage(Integer.parseInt(request.queryParams("id")), request.queryParams("chatroomName"));
            return gson.toJson("delete message");
        });

        post("/chatroom/removeUser", (request, response) -> {
            da.banUser(request.queryParams("user"), request.queryParams("chatroomName"));
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
