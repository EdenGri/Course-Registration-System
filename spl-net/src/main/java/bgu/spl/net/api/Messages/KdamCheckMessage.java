package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class KdamCheckMessage implements Message {
    private int courseNum;
    public KdamCheckMessage(int courseNum){

        this.courseNum=courseNum;
    }
}
