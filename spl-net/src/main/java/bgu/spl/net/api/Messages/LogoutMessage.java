package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

public class LogoutMessage implements Message {

    public LogoutMessage() {
    }

    ;

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user != null) {
            database.getConnectedUsers().remove(user.getName());
            user.logout();//todo need?
            return new AckMessage((short) 4, null);
        }
        return new ErrorMessage((short) 4);
    }
}
