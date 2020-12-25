package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class CourseRegMessage implements Message {
    int courseNum;

    public CourseRegMessage(int courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user != null && user instanceof Student) {
            Course course = database.getCourses().get(courseNum);
            if (course != null) {
                if (course.isAvailable()) {
                    if (((Student) user).haveAllKdamCourses(course)) {




                        return new AckMessage((short) 5, null);
                    }
                }
            }
        }
        return new ErrorMessage((short) 5);
    }
}
