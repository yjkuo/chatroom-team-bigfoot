package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.WebSocketAdapter;
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

        get("/signup", (request, response) -> {
            return gson.toJson("signup");
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
