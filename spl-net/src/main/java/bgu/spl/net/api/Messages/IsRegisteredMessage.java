package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class IsRegisteredMessage implements Message {
    int courseNum;
    public IsRegisteredMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }

    //todo check what if admin asks
    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user instanceof Student){
            Course course=database.getCourses().get(courseNum);
            if (course!=null) {
                if (course.isRegistered(user)) {
                    return new AckMessage<>((short) 9, "REGISTERED");
                }
                else {
                    return new AckMessage<>((short) 9, "NOT REGISTERED");
                }
            }
        }
        return new ErrorMessage((short)9);
    }
}
