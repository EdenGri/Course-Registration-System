package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class ServerToClientMessage implements Message {
    protected short MessageOpcode;

    public ServerToClientMessage(short MessageOpcode){
        this.MessageOpcode = MessageOpcode;
    }

    public short getMessageOpcode(){
        return MessageOpcode;
    }
}
