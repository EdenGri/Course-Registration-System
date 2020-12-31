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
            Course course = database.getCourses().get(courseNum);
            if (course != null) {
                String optional = course.getKdamCoursesList().toString();
                return new AckMessage((short) 6, optional);//todo check the tostring
            }
        }
        return new ErrorMessage((short)6);
    }
}
