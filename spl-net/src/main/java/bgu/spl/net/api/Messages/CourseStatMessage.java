package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class CourseStatMessage implements Message {
    private int courseNum;
    public CourseStatMessage(int courseNum){
        this.courseNum=courseNum;
    }
}
