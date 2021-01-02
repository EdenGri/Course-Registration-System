package bgu.spl.net.impl.RegistrationSystem;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class User {
    protected String username;
    protected String password;
    protected AtomicBoolean isLoggedIn = new AtomicBoolean(false);

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Returns the name of the user
    public String getName() {
        return username;
    }

    //Returns the password of the user
    public String getPassword() {
        return password;
    }

    //Returns true if the user is logged in
    public boolean getIsLoggedIn() {
        return isLoggedIn.get();
    }

    //Returns true if login is done successfully
    public boolean login() {
        return isLoggedIn.compareAndSet(false, true);
    }

    //Returns true if login is done successfully
    public boolean logout() {
        return isLoggedIn.compareAndSet(true, false);
    }
}
