package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class AckMessage<T> extends ServerToClientMessage {

    private T Response;

    public AckMessage(short MessageOpcode, T response) {
        super(MessageOpcode);
        Response = response;
    }

}

