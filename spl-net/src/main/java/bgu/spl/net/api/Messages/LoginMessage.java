package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;
import java.nio.charset.StandardCharsets;

public class LoginMessage implements Message {
    private String username;
    private String password;

    public LoginMessage(byte[] info, int length){
        String decodedString = new String(info, 0, length, StandardCharsets.UTF_8);
        String[] splitString = decodedString.split("\0");
        username = splitString[0];
        password = splitString[1];
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }


}
