package bgu.spl.net.impl.RegistrationSystem;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class User {
    protected String username;
    protected String password;
    protected AtomicBoolean isLoggedIn=new AtomicBoolean(false);

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

    public boolean getIsLoggedIn(){
        return isLoggedIn.get();
    }


    public boolean login(){
        return isLoggedIn.compareAndSet(false,true);
    }
    public boolean logout(){
        return isLoggedIn.compareAndSet(true,false);
    }
}
