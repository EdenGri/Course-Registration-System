package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Admin;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Student;
import bgu.spl.net.impl.RegistrationSystem.User;

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
    public Message execute(Database database) {

        User user = new Student(username, password);
        User toAdd = database.getRegisteredUsers().putIfAbsent(username, user);
        if (toAdd != null) {
            return new AckMessage((short) 2);//todo need to update the user name maby in optinal
        } else {
            return new ErrorMessage((short) 2);
        }

    }
}

