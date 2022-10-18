package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.user.AUser;

public interface IMessageCmd {
    void execute(AMessage context);
}
