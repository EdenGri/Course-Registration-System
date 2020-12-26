package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.Student;
import bgu.spl.net.impl.RegistrationSystem.User;

public class MyCoursesMessage implements Message {
    @Override
    public Message execute(Database database, Session session) {
        User user= session.getUser();
        if (user!=null){
            if (user instanceof Student) {
                String myCourses=((Student) user).getRegisteredCoursesToString();
                return new AckMessage<>((short) 11,myCourses);
            }
        }
        return new ErrorMessage((short)11);//todo needs to send error?
    }
}
