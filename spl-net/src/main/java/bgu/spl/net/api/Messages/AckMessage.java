package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class AckMessage<String> extends ServerToClientMessage {

    private String response;

    public AckMessage(short MessageOpcode, String response) {
        super(MessageOpcode);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

}

