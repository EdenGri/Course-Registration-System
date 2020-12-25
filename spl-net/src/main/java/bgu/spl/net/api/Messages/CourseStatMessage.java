package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Course;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;

public class CourseStatMessage implements Message {
    private int courseNum;
    public CourseStatMessage(int courseNum){
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        Course course=database.getCourses().get(courseNum);
        return new AckMessage<>((short)7,course.courseStat());
    }
}
