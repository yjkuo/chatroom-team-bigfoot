package edu.rice.comp504.model;

import com.google.gson.JsonObject;

import static j2html.TagCreator.p;

/**
 * Send messages to the client.
 */
public class MsgToClientSender {

    /**
     * Broadcast message to all users.
     * @param sender  The message sender.
     * @param message The message.
     */
    public static void broadcastMessage(String sender, String message) {
        UserDB.getSessions().forEach(session -> {
            try {
                JsonObject jo = new JsonObject();
                // TODO: use .addProperty(key, value) add a JSON object property that has a key "userMessage"
                //  and a j2html paragraph value
                jo.addProperty("userMessage", p(sender + " says: " + message).render());
                session.getRemote().sendString(String.valueOf(jo));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
