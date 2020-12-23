package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;

public class LogoutMessage implements Message {

    public LogoutMessage(){};

    @Override
    public Message execute(Database database) {
        if (database.getConnectedUsers().contains(user)){
            database.getConnectedUsers().remove(user);
            return new AckMessage((short)4);
        }
        return new ErrorMessage((short)4);
    }
}
