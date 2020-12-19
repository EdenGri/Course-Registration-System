package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.Database;
import bgu.spl.net.api.MessageEncoderDecoderImpl;
import bgu.spl.net.api.MessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main (String[] args){
        Database database =Database.getInstance();
        int port = Integer.parseInt(args[0]);


        Server.threadPerClient(
                port, //port
                () -> new MessagingProtocolImpl(database), //protocol factory
                MessageEncoderDecoderImpl::new //message encoder decoder factory
        ).serve();{
                }
    }
}