package bgu.spl.net.impl.RegistrationSystem;

public class Session {
    User user;
    boolean shouldLogout=false;

    public Session(User user){
        this.user=user;
    }

    //Returns user
    public User getUser(){
        return user;
    }

    //Set user
    public void setUser(User user){
        this.user=user;
    }

    public boolean getShouldLogout(){
        return shouldLogout;
    }

    public void setShouldLogout(boolean b) {
        shouldLogout=b;
    }
}
