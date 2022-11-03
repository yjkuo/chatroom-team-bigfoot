package edu.rice.comp504.adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.rice.comp504.model.DispatcherAdapter;
import edu.rice.comp504.model.DispatcherAdapter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.HashMap;

/**
 * Create a web socket for the server.
 */
@WebSocket
public class WebSocketAdapter {
    DispatcherAdapter co = DispatcherAdapter.makeDispatcher();
    Gson gson = new Gson();

    /**
     * Open user's session.
     * @param session The user whose session is opened.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {
        try {
            session.getRemote().sendString("connectNow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the user's session.
     * @param session The use whose session is closed.
     */
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        co.offline("", session);
    }

    /**
     * Send a message.
     * @param session  The session user sending the message.
     * @param message The message to be sent.
     */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        if (message.equals("")) {
            return;
        }
        HashMap<String,String> messageMap = gson.fromJson(message, new TypeToken<HashMap<String, String>>(){}.getType());
        if (messageMap.get("type").equals("initialize")) {
            co.online(messageMap.get("username"), session);
        }
    }
}
