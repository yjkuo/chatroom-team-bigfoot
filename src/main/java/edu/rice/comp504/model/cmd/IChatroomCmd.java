package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.chatroom.AChatroom;

/**
 * The IChatroomCmd is an interface used to pass commands to chatrooms in the central organizer.  The
 *  * chatroom must execute the command.
 */
public interface IChatroomCmd {

    /**
     * Execute the command.
     * @param context The receiver chatroom on which command will be executed.
     */
    void execute(AChatroom context);
}
