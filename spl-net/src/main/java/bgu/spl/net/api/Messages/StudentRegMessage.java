package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.nio.charset.StandardCharsets;

public class StudentRegMessage implements Message {
    private String username;
    private String password;

    //constructor
    public StudentRegMessage(byte[] info, int length){
        super();
        String decodedString = new String(info, 0, length, StandardCharsets.UTF_8);
        String[] splitString = decodedString.split("\0");
        this.username = splitString[0];
        this.password = splitString[1];

    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }
}

