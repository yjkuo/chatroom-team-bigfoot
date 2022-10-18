package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.user.AUser;

/**
 * The IUserCmd is an interface used to pass commands to users in the central organizer.  The
 *  * chatroom must execute the command.
 */
public interface IUserCmd {
    /**
     * Execute the command.
     * @param context The receiver user on which command will be executed.
     */
    void execute(AUser context);
}
