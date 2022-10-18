package edu.rice.comp504.model.cmd;

/**
 * A factory that makes command.
 */
public interface ICmdFactory {
    /**
     * Make user command.
     * @param type String type of commands to make.
     * @return Commands to be make or null command
     */
    IUserCmd makeUserCmd(String type);

    /**
     * Make chatroom command.
     * @param type String type of commands to make.
     * @return Commands to be make or null command
     */
    IChatroomCmd makeChatroomCmd(String type);

    /**
     * Make message command.
     * @param type String type of commands to make.
     * @return Commands to be make or null command
     */
    IMessageCmd makeMessageCmd(String type);
}
