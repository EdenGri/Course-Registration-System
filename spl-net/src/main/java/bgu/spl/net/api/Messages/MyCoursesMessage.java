package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;

public class MyCoursesMessage implements Message {
    @Override
    public Message execute(Database database, Session session) {
        return null;
    }
}
