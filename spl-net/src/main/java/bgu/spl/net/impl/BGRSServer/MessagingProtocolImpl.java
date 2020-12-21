package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.api.Message;
import bgu.spl.net.api.Messages.*;
import bgu.spl.net.api.MessagingProtocol;

public class MessagingProtocolImpl implements MessagingProtocol<Message> {

    private boolean shouldTerminate;
    private Database database;

    public MessagingProtocolImpl(Database database){
        this.database = database;
        shouldTerminate = false;
    }

    @Override
    public Message process(Message message) {

        if(message instanceof AdminRegMessage || message instanceof StudentRegMessage){

        }


    }

    @Override
    public boolean shouldTerminate() {

        return shouldTerminate;
    }
}
