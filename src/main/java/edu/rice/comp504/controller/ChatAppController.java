package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.WebSocketAdapter;
import edu.rice.comp504.model.CentralOrganizer;
import spark.utils.IOUtils;

import java.io.BufferedReader;
import java.io.FileReader;

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
        CentralOrganizer co = CentralOrganizer.makeOrganizer();

        post("/register", (request, response) -> {
            return gson.toJson("register");
        });

        post("/login", (request, response) -> {
            return gson.toJson("login");
        });

        get("/chatroom/getChatroomList", (request, response) -> {
            return gson.toJson("get chatroom list");
        });

        post("/chatroom/createChatroom", (request, response) -> {
            return gson.toJson("create chatroom");
        });

        post("/chatroom/joinChatroom", (request, response) -> {
            return gson.toJson("join chatroom");
        });

        post("/chatroom/connectToChatroom", (request, response) -> {
            return gson.toJson("Connect to chat room");
        });

        post("/chatroom/leaveChatroom", (request, response) -> {
            return gson.toJson("leave chatroom");
        });

        post("/chatroom/inviteToChatroom", (request, response) -> {
            return gson.toJson("Invite To chatroom");
        });

        get("/chatroom/userList", (request, response) -> {
            return gson.toJson("user list");
        });

        get("/chatroom/getAdmin", (request, response) -> {
            return gson.toJson("get admin");
        });

        get("/chatroom/getMessages", (request, response) -> {
            return gson.toJson("get messages");
        });

        post("/chatroom/sendMessage", (request, response) -> {
            return gson.toJson("send message");
        });

        post("/chatroom/editMessage", (request, response) -> {
            return gson.toJson("edit message");
        });

        post("/chatroom/deleteMessage", (request, response) -> {
            return gson.toJson("delete message");
        });

        post("/chatroom/warnUser", (request, response) -> {
            return gson.toJson("warn user");
        });

        post("/chatroom/banUserFromAll", (request, response) -> {
            return gson.toJson("ban user from all room");
        });

        post("/chatroom/removeUser", (request, response) -> {
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
