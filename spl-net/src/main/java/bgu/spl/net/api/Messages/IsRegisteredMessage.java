package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Course;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

public class IsRegisteredMessage implements Message {
    int courseNum;
    public IsRegisteredMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }

    //TODO : if student sends course num that doesn't exist return error , aLso is user is not logged in return error, also check forum for my qs

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user!=null){
            Course course=database.getCourses().get(courseNum);
            if (course.isRegistered(user)){
                return new AckMessage<>((short)9,"REGISTERED");
            }
        }
        return new AckMessage<>((short)9,"NOT REGISTERED");//todo dont sure if needs to return ack
    }
}
