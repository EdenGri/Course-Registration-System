package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class CourseStatMessage implements Message {
    private Short courseNum;

    public CourseStatMessage(Short courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    //admin tries to receive the state of a specific course
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user instanceof Admin) {
            if (user.getIsLoggedIn()) {
                String courseStat = database.getCourseStat(courseNum);
                if (courseStat != null) {
                    return new AckMessage<>((short) 7, courseStat);
                }
            }
        }
        return new ErrorMessage((short) 7);
    }
}
