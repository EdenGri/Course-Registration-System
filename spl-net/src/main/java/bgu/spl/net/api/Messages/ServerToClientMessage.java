package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Database;

public class ServerToClientMessage implements Message {
    protected short MessageOpcode;

    public ServerToClientMessage(short MessageOpcode){
        this.MessageOpcode = MessageOpcode;
    }

    public short getMessageOpcode(){
        return MessageOpcode;
    }

    @Override
    public Message execute(Database database) {
        return null;
    }
}
