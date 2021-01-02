package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class CourseRegMessage implements Message {
    Short courseNum;

    public CourseRegMessage(Short courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    //registers the student to the course the student wants to register to
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (database.CourseReg(user, courseNum)) {
            return new AckMessage<>((short) 5, null);
        }
        return new ErrorMessage((short) 5);
    }
}
