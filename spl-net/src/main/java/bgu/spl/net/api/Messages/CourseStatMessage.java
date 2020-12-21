package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class CourseStatMessage implements Message {
    private int courseNun;
    public CourseStatMessage(int courseNum){
        this.courseNun=courseNum;
    }
}
