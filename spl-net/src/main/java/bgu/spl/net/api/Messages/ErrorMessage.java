package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class ErrorMessage extends ServerToClientMessage {

    public ErrorMessage(short MessageOpcode){
        super(MessageOpcode);
    }

}
