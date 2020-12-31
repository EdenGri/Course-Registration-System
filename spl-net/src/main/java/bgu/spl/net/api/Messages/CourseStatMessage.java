package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class CourseStatMessage implements Message {
    private int courseNum;
    public CourseStatMessage(int courseNum){
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {//todo add snync?
        User user= session.getUser();
        if (user instanceof Admin) {
            String courseStat = database.getCourseStat(courseNum);
            if (courseStat!=null) {
                return new AckMessage<>((short) 7, courseStat);
            }
        }
        return new ErrorMessage((short) 7);
    }
}
