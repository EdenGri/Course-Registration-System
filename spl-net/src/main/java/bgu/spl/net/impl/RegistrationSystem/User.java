package bgu.spl.net.impl.RegistrationSystem;

import java.util.LinkedList;

public abstract class User {
    private String username;
    private String password;
    private boolean isRegistered; //todo remove maybe?
    private volatile boolean isLoggedIn;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isRegistered = true;
        isLoggedIn = false;
    }

    public String getName(){
        return username;
    }
    public void login(){
        isLoggedIn = true;
    }
    public void logout(){
        isLoggedIn = false;
    }
    public String getPassword() {
        return password;
    }

}
