package bgu.spl.net.api;
import bgu.spl.net.api.Database;
import bgu.spl.net.api.Message;

public class MessagingProtocolImpl implements MessagingProtocol<Message>{

    private boolean shouldTerminate;
    private Database database;

    public MessagingProtocolImpl(Database database){
        this.database = database;
        shouldTerminate = false;
    }

    @Override
    public Message process(Message msg) {
        return null;
        //TODO
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
