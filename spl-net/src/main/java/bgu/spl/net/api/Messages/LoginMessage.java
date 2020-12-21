package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
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


}
