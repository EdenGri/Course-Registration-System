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

    public LoginMessage(String userName, String password){
        super();
        this.userName = userName;
        this.password = password;
    }

    public String getUsername(){
        return userName;
    } //todo need? didnt we do this in user?

    public String getPassword(){
        return password;
    } //todo need? didnt we do this in user?


    @Override
    public Message execute(Database database, Session session) {
        User user = database.getRegisteredUsers().get(userName);
        if (user!=null){
            if (user.getPassword().equals(password)){
                if (session.getUser()==null){
                    session.setUser(user);
                    return new AckMessage((short)3,null);
                }
            }
        }
        return new ErrorMessage((short)3);
    }
}
