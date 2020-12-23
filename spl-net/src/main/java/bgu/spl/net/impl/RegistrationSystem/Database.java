package bgu.spl.net.impl.RegistrationSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private ConcurrentHashMap<String, User> registeredUsers;
    private ConcurrentHashMap<String,User> connectedUsers;
    private ConcurrentHashMap<Integer, Course> courses;
    private ConcurrentHashMap<User, ConcurrentLinkedQueue<Integer>> usersList;
    private ConcurrentHashMap<Course, ConcurrentLinkedQueue<String>> coursesList; //todo which version ASKEDEN




    //to prevent user from creating new Database
    private Database() {
        // TODO: implement
    }

    /**
     * Retrieves the single instance of this class.
     */

    public static Database getInstance() {

        return Singleton.instance;
    }

    public ConcurrentHashMap<String, User> getRegisteredUsers() {
        return registeredUsers;
    }

    public ConcurrentHashMap<String, User> getConnectedUsers() {
        return connectedUsers;
    }
    public void logoutUser(User user){
        connectedUsers.remove(user.getName());
    }

    private static class Singleton{
        private static Database instance = new Database();
    }


    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        // TODO: implement
        return false;
    }


}