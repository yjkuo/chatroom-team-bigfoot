package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.user.AUser;

public interface IUserCmd {

    void execute(AUser context);
}
