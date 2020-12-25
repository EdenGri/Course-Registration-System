package bgu.spl.net.impl.RegistrationSystem;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    private static class Singleton{
        private static Database instance = new Database();
    }

    public ConcurrentHashMap<String, User> getRegisteredUsers() {
        return registeredUsers;
    }
    public ConcurrentHashMap<Integer, Course> getCourses() {
        return courses;
    }

    public ConcurrentHashMap<String, User> getConnectedUsers() {
        return connectedUsers;
    }
    public void logoutUser(User user){
        connectedUsers.remove(user.getName());
    }




    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) { //todo this part is not finished
        ArrayList<String> courses;
        try {
            courses = (ArrayList<String>) Files.readAllLines(Paths.get(coursesFilePath));
        } catch (Exception e){e.printStackTrace();}


        for(String course: courses){
                try{
                    String[] splitString = course.split("\\|");
                } catch (Exception e){e.printStackTrace();}
            }
        }
    }


