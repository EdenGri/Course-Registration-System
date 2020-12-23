package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class AckMessage extends ServerToClientMessage {

    public AckMessage(short MessageOpcode) {
        super(MessageOpcode);
    }

}

