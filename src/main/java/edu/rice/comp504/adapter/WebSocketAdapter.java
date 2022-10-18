package edu.rice.comp504.adapter;

import edu.rice.comp504.model.CentralOrganizer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * Create a web socket for the server.
 */
@WebSocket
public class WebSocketAdapter {
    CentralOrganizer co = CentralOrganizer.makeOrganizer();

    /**
     * Open user's session.
     * @param session The user whose session is opened.
     */
    @OnWebSocketConnect
    public void onConnect(Session session) {
        co.online("", session);
    }

    /**
     * Close the user's session.
     * @param session The use whose session is closed.
     */
    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        co.offline("", session);
    }
}
