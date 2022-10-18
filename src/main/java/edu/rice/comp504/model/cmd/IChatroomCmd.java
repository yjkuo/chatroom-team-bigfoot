package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.chatroom.AChatroom;

public interface IChatroomCmd {

    void execute(AChatroom context);
}
