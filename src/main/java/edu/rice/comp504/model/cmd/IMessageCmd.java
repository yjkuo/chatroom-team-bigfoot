package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.AUser;

/**
 * The IMessageCmd is an interface used to pass commands to messages in the central organizer.  The
 *  * chatroom must execute the command.
 */
public interface IMessageCmd {
    /**
     * Execute the command.
     * @param context The receiver message on which command will be executed.
     */
    void execute(AMessage context);
}
