package edu.rice.comp504.model.cmd;

public interface ICmdFactory {
    IUserCmd makeUserCmd(String type);

    IChatroomCmd makeChatroomCmd(String type);

    IMessageCmd makeMessageCmd(String type);
}
