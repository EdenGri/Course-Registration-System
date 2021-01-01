package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;
import bgu.spl.net.impl.BGRSServer.MessagingProtocolImpl;

public class LogoutMessage implements Message {

    public LogoutMessage() {
    }

    @Override
    public Message execute(Database database, Session session) {//todo check sync
        User user = session.getUser();
        if (user != null) {
            session.setUser(null);
            session.setShouldLogout(true);
            return new AckMessage((short) 4, null);
        }
        return new ErrorMessage((short) 4);
    }
}
