package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class StudentStatMessage implements Message {
    String StudentUserName;

    public StudentStatMessage(String userName){
        super();
        this.StudentUserName=userName;
    }

    //todo check if admin and if so return error

    @Override
    public Message execute(Database database, Session session) {
        User user=session.getUser();
        if (user instanceof Admin) {
            String studentStat = database.getStudentStat(StudentUserName);
            return new AckMessage<>((short) 8, studentStat);
        }
        return new ErrorMessage((short)8);
    }
}
