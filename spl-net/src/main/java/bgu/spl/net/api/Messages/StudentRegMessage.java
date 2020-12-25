package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.*;

import java.nio.charset.StandardCharsets;

public class StudentRegMessage implements Message {
    private String username;
    private String password;

    //constructor
    public StudentRegMessage(String username, String password) {
        super();
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Message execute(Database database, Session session) {
        User user = new Student(username, password);
        User toAdd = database.UserReg(username, user);
        if (toAdd != null) {
            return new AckMessage((short) 2,null);
        } else {
            return new ErrorMessage((short) 2);
        }
    }
}

