package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main (String[] args){
        Database database =Database.getInstance();
        int port = Integer.parseInt(args[0]);//todo


        Server.threadPerClient(
                port, //port//todo change to port
                () -> new MessagingProtocolImpl(database), //protocol factory
                MessageEncoderDecoderImpl::new //message encoder decoder factory
        ).serve();

    }
}
