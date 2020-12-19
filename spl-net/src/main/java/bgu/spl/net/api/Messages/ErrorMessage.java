package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class ErrorMessage implements Message {
    private short opcode;

    public ErrorMessage(short opcode){
        this.opcode = opcode;
    }

    public short getOpcode() {
        return opcode;
    }
}
