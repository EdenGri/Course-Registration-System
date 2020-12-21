package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.nio.charset.StandardCharsets;

public class StudentRegMessage implements Message {
    private String username;
    private String password;

    //constructor
    public StudentRegMessage(String username, String password){
        super();
        this.username = username;
        this.password = password;

    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }
}

