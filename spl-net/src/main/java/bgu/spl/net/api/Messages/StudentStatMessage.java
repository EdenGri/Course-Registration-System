package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Course;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.Student;

public class StudentStatMessage implements Message {
    String StudentUserName;

    public StudentStatMessage(String userName){
        super();
        this.StudentUserName=userName;
    }

    @Override
    public Message execute(Database database, Session session) {
        String studentStat=database.getStudentStat(StudentUserName);
        return new AckMessage<>((short)8,studentStat);
    }
}
