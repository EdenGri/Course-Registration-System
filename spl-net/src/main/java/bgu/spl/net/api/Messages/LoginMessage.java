package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.impl.RegistrationSystem.Admin;
import bgu.spl.net.impl.RegistrationSystem.Database;
import bgu.spl.net.impl.RegistrationSystem.Session;
import bgu.spl.net.impl.RegistrationSystem.User;

import java.nio.charset.StandardCharsets;

public class LoginMessage implements Message {
    private String username;
    private String password;

    public LoginMessage(String userName, String password){
        super();
        this.username = userName;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }


    @Override
    public Message execute(Database database, Session session) {
        User user = database.getRegisteredUsers().get(username);
        if (user!=null){
            if (user.getPassword().equals(password)){
                User toAdd = database.getConnectedUsers().putIfAbsent(username,user);
                if (toAdd!=null){
                    session.setUser(user);
                    session.getUser().login();//todo need??
                    return new AckMessage((short)3,null);
                }
            }
        }
        return new ErrorMessage((short)3);
    }
}
