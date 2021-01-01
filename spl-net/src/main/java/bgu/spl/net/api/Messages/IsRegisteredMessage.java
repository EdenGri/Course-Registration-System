package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class IsRegisteredMessage implements Message {
    Short courseNum;
    public IsRegisteredMessage(Short courseNum){
        super();
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {//todo check sync probably no need
        User user = session.getUser();
        if (user instanceof Student){
            if (user.getIsLoggedIn()) {
                Course course = database.getCourses().get(courseNum);
                if (course != null) {
                    if (course.isRegistered(user)) {
                        return new AckMessage<>((short) 9, "REGISTERED");
                    } else {
                        return new AckMessage<>((short) 9, "NOT REGISTERED");
                    }
                }
            }
        }
        return new ErrorMessage((short)9);
    }
}
