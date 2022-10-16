package edu.rice.comp504.model;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores chap app user information.
 */
public class UserDB {
    private static final Map<Session,String> sessionUserMap =  new ConcurrentHashMap<>();
    private static int nextUserId = 1;

    /**
     * Constructor.
     */
    public UserDB() {
    }

    /**
     * Get the session to username map.
     * @return The session to username map
     */
    public static Map<Session,String> getSessionUserMap() {
        return sessionUserMap;
    }

    /**
     * Generate the next user id.
     * @return The next user id
     */
    public static int genNextUserId() {
        return nextUserId++;
    }

    /**
     * Add a session user.
     * @param session The session.
     * @param username The username.
     */
    public static void addSessionUser(Session session, String username) {
        sessionUserMap.put(session, username);
    }

    /**
     * Get the user.
     * @param session The session.
     * @return The username
     */
    public static String getUser(Session session) {
        return sessionUserMap.get(session);
    }

    /**
     * Remove user.
     * @param session The session.
     */
    public static void removeUser(Session session) {
        sessionUserMap.remove(session);
    }

    /**
     * Get open sessions.
     * @return All open sessions
     */
    public static Set<Session> getSessions() {
        return sessionUserMap.keySet();
    }
}
