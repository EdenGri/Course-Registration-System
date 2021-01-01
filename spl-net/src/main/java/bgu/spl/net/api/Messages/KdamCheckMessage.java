package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class KdamCheckMessage implements Message {
    private Short courseNum;
    public KdamCheckMessage(Short courseNum){

        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user instanceof Student) {
            if (user.getIsLoggedIn()) {
                Course course = database.getCourses().get(courseNum);
                if (course != null) {
                    String kdamCourses = course.KdamCoursesToString();
                    return new AckMessage((short) 6, kdamCourses);
                }
            }
        }
        return new ErrorMessage((short)6);
    }
}
