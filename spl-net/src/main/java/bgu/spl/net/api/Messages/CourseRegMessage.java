package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class CourseRegMessage implements Message {
    int courseNum;
    public CourseRegMessage(int courseNum){
        this.courseNum=courseNum;
    }

}
