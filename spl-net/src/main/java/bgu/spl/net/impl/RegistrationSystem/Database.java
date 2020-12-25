package bgu.spl.net.impl.RegistrationSystem;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

    public User UserReg(String userName,User user){
        return registeredUsers.putIfAbsent(userName,user);
    }

    public User login(String username,User user){
        user.login();//todo ask ofry if needed?
        return connectedUsers.putIfAbsent(username,user);
    }

    public void logOutUser(User user){
        connectedUsers.remove(user.getName());
    }





}




    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) { //todo can we just change method signature to throw exception
        ArrayList<String> listOfCourses =
        try {
            listOfCourses = (ArrayList<String>) Files.readAllLines(Paths.get(coursesFilePath));

            for (String course : listOfCourses) {
                String[] splitString = course.split("\\|"); //todo check if only "|"
                int courseNum = Integer.parseInt(splitString[0]);
                String courseName = splitString[1];
                Course addCourse = new Course(courseName,courseNum);
                courses.put(courseNum,addCourse);

                String kdamCourses = splitString[2];
                ArrayList kdamCourses =; //TODO
                addCourse.setKdamCourses(kdamCourses);


                int numOfMaxStudents = Integer.parseInt(splitString[3]);
                addCourse.setNumOfMaxStudents(numOfMaxStudents);


            }
        } catch (Exception e){ e.printStackTrace(); // todo return false?}


        }
    }


