package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Student;
import bgu.spl.net.impl.RegistrationSystem.Course;
import bgu.spl.net.impl.RegistrationSystem.User;

public class CourseRegMessage implements Message {
    int courseNum;
    public CourseRegMessage(int courseNum){
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database) {
        User user=database.getConnectedUsers().get(user);
        if (user!=null&&user instanceof Student){
            Course course = database.getCourses().get(courseNum);
            if (course!=null){
                if (course.isAvailable()){
                    if (((Student) user).haveAllKdamCourses(course)){
                        return new AckMessage((short)5);
                    }
                }
            }
        }
        return new ErrorMessage((short)5);
    }
}
