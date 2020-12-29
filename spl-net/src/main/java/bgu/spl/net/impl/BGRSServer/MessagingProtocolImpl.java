package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.impl.RegistrationSystem.*;
import bgu.spl.net.api.Message;
import bgu.spl.net.api.Messages.*;
import bgu.spl.net.api.MessagingProtocol;


import java.util.ArrayList;

public class MessagingProtocolImpl implements MessagingProtocol<Message> {
    private boolean shouldTerminate;
    private Database database;
    private Session session;


    public MessagingProtocolImpl(Database database){
        this.database = database;
        shouldTerminate = false;
        session=new Session(null);
    }

    @Override
    public Message process(Message message) {
        Message response = message.execute(database,session);
        shouldTerminate=session.getShouldLogout();
        return response;
    }


    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
