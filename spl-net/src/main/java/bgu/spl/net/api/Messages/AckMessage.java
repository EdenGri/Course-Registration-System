package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class AckMessage implements Message {
    private short opcode;

    public AckMessage(short opcode){
        this.opcode = opcode;
    }

    public short getOpcode(){
        return opcode;
    }
}

