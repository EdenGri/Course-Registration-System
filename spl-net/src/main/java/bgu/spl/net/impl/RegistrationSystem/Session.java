package bgu.spl.net.impl.RegistrationSystem;

public class Session {
    User user;
    boolean shouldLogout = false;

    public Session(User user) {
        this.user = user;
    }

    //Returns user
    public User getUser() {
        return user;
    }

    //Sets user
    public void setUser(User user) {
        this.user = user;
    }

    //should logout getter
    public boolean getShouldLogout() {
        return shouldLogout;
    }

    //should logout setter
    public void setShouldLogout(boolean b) {
        shouldLogout = b;
    }
}
