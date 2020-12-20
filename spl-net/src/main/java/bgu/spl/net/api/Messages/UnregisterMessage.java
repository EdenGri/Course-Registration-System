package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class UnregisterMessage implements Message {
    int courseNum;
    public UnregisterMessage(int courseNum){
        super();
        this.courseNum=courseNum;
    }
}
