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
        get("/main", (request, response) -> {
            String result = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Hello</title>
                        <script src="js/jquery-3.6.0.min.js"></script>
                        <script src="js/chatApp.js"></script>
                        <!-- CSS only -->
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
                        <link rel="stylesheet" href="chatApp.css">
                    </head>
                    <body>
                        <div class="container">
                            <div class="row pt-5">
                                <div class="col-3">
                                    <!-- Tab links -->
                                    <div class="tab">
                                      <button class="tablinks" id="btn-chatrooms" onclick="openTab(event, 'tab-chatrooms')">Chat Rooms</button>
                                      <button class="tablinks" id="btn-join" onclick="openTab(event, 'tab-join')">Join</button>
                                      <button class="tablinks" id="btn-create" onclick="openTab(event, 'tab-create')">Create</button>
                                    </div>
                                    
                                    <!-- Tab content -->
                                    <div id="tab-chatrooms" class="tabcontent">
                                      <h3>London</h3>
                                      <p>London is the capital city of England.</p>
                                      <p>London is the capital city of England.</p>
                                    </div>
                                    
                                    <div id="tab-join" class="tabcontent">
                                      <h3>Paris</h3>
                                      <p>Paris is the capital of France.</p>
                                    </div>
                                    
                                    <div id="tab-create" class="tabcontent">
                                      <h3>Tokyo</h3>
                                      <p>Tokyo is the capital of Japan.</p>
                                    </div>
                                    <script>
                                        document.getElementById("btn-chatrooms").click();
                                    </script>
                                </div>
                            </div>
                        </div>
                    </body>
                    </html>           
                    """;
            return result;
        });

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
