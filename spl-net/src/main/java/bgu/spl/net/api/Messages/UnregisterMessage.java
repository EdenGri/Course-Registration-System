package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

public class UnregisterMessage implements Message {
    int courseNum;
    public UnregisterMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (database.CourseUnregistered(user, courseNum)) {
            return new AckMessage<>((short) 10, null);
        }
        return new ErrorMessage((short) 10);
    }
}
