package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.impl.RegistrationSystem.Admin;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.api.Message;
import bgu.spl.net.api.Messages.*;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.RegistrationSystem.Student;
import bgu.spl.net.impl.RegistrationSystem.User;


import java.util.ArrayList;

public class MessagingProtocolImpl implements MessagingProtocol<Message> {

    private boolean shouldTerminate;
    private Database database;
    private String userName;


    public MessagingProtocolImpl(Database database){
        this.database = database;
        shouldTerminate = false;
        userName=null;//todo how we get the user name maby in reg
    }

    @Override
    public Message process(Message message) {
        Message response= message.execute(database);




        else if (message instanceof StudentRegMessage) {
            User user = new Student(((StudentRegMessage) message).getUsername(), ((StudentRegMessage) message).getPassword());
            this.database = Database.getInstance();//todo check like this??
            User toAdd = database.getRegisteredUsers().putIfAbsent(((StudentRegMessage) message).getUsername(), user);
            if (toAdd != null) {
                connections.send(connectionId, new ErrorMsg((short) 1)); //todo if already registered send error message
            } else {
                connections.send(connectionId, new ACKMsgStandart((short) 1)); //todo if successful send ack message
            }
        } else if (message instanceof LoginMessage) {
            this.database = Database.getInstance();//todo check like this??
            String username = ((LoginMessage) message).getUsername();
            User user = database.getRegisteredUsers().get(username); //
            User userToLogin = database.getConnectedUsers().putIfAbsent(username, user);
            if (user == null || user.isLoggedIn() || !(((LoginMessage) message).getPassword().equals(user.getPassword())) || userToLogin != null)
                connections.send(connectionId, new ErrorMsg((short) 2)); //todo send error message
            else {
                synchronized (user) {
                    user.login();
                    //todo send ack message
                }
            }
        } else if (message instanceof LogoutMessage) {
            this.database = Database.getInstance();//todo check like this?? or we dont need this bc of tcp/reactor server
            String username = ((LogoutMessage) message).getUsername(); //todo figure out how to get username or user
            User user = database.getConnectedUsers().get(username);
            if (user == null || !user.isLoggedIn()) {
                connections.send(connectionId, new ErrorMsg((short) 3)); //todo send error message
            } else {
                synchronized (user) {
                    user.logout();
                    database.logoutUser(user);
                    //todo send ack message
                }
            }
        } else if (message instanceof CourseRegMessage) {
            this.database = Database.getInstance();//todo check like this?? or we dont need this bc of tcp/reactor server

        } else if (message instanceof KdamCheckMessage) {
            //TODO

        } else if (message instanceof CourseStatMessage) {
            //TODO
        } else if (message instanceof StudentStatMessage) {
            //TODO
        } else if (message instanceof IsRegisteredMessage) {
            //TODO
        } else if (message instanceof UnregisterMessage) {
            //TODO
        } else if (message instanceof MyCoursesMessage) {
            //TODO
        }

    }

            @Override
    public boolean shouldTerminate() {

        return shouldTerminate;
    }
}
