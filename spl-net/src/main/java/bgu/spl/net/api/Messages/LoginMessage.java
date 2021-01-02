package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Admin;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

import java.nio.charset.StandardCharsets;

public class LoginMessage implements Message {
    private String userName;
    private String password;

    public LoginMessage(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Message execute(Database database, Session session) {//logins a user into the server
        User user = database.getRegisteredUsers().get(userName);
        //checks if user is registered, the password is correct, and if the user is not logged in
        if (user != null && user.getPassword().equals(password)
                && session.getUser() == null && user.login()) {

            session.setUser(user);
            return new AckMessage((short) 3, null);

        }
        return new ErrorMessage((short) 3);
    }
}
