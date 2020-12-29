package bgu.spl.net.impl.RegistrationSystem;

import java.util.LinkedList;

public abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName(){
        return username;
    }
    public String getPassword() {
        return password;
    }

}
