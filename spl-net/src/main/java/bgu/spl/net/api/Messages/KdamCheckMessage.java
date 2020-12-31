package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class KdamCheckMessage implements Message {
    private int courseNum;
    public KdamCheckMessage(int courseNum){

        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user instanceof Student) {
            Course course = database.getCourses().get(courseNum);
            if (course != null) {
                return new AckMessage((short) 6, course.getKdamCoursesList().toString());//todo check the tostring
            }
        }
        return new ErrorMessage((short)6);
    }
}
