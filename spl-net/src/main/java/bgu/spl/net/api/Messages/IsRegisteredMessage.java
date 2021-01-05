package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

public class IsRegisteredMessage implements Message {
    Short courseNum;

    public IsRegisteredMessage(Short courseNum) {
        super();
        this.courseNum = courseNum;
    }

    @Override
    //Returns if the student is registered to the specified course
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user instanceof Student && user.getIsLoggedIn()) {
            Course course = database.getCourses().get(courseNum);
            //checks if the course exists and if the user is registered to it
            if (course != null) {
                if (course.isRegistered(user)) {
                    return new AckMessage<>((short) 9, "REGISTERED");
                }
                else {
                    return new AckMessage<>((short) 9, "NOT REGISTERED");
                }
            }
        }
        return new ErrorMessage((short) 9);
    }
}
