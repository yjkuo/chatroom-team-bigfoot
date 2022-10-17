package edu.rice.comp504;

import edu.rice.comp504.model.user.User;

/**
 * The class to maintain and organize the state of chat app.
 */
public interface CentralOrganizer {

    public User register();

    public User login();

    //TODO include other methods that will be used to accomplish certain tasks (like the ball world store)
}
