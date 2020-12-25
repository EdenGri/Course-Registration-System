package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

public class IsRegisteredMessage implements Message {
    int courseNum;
    public IsRegisteredMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = session.getUser();
        if (user!=null){

        }
        return new AckMessage<>((short)9,"NOT REGISTERED");
    }
}
