package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main (String[] args){
        Database database = Database.getInstance();
        int port = Integer.parseInt(args[0]);
        int threads = Integer.parseInt(args[1]);

        Server.threadPerClient(threads,
                port, //port
                () -> new MessagingProtocolImpl(database), //protocol factory
                MessageEncoderDecoderImpl::new //message encoder decoder factory
        ).serve();
        }
    }

