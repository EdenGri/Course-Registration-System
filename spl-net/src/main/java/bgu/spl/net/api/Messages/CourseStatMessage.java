package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class CourseStatMessage implements Message {
    private int curseNun;
    public CourseStatMessage(int curseNum){
        this.curseNun=curseNum;
    }
}
