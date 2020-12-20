package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class StudentStatMessage implements Message {
    String userName;

    public StudentStatMessage(String userName){
        super();
        this.userName=userName;
    }
}
