package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class KdamCheckMessage implements Message {
    private int curseNun;
    public KdamCheckMessage(int curseNum){
        this.curseNun=curseNum;
    }
}
