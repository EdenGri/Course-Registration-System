package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class IsRegisteredMessage implements Message {
    int courseNum;
    public IsRegisteredMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }
}
