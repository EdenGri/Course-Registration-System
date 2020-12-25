package bgu.spl.net.impl.RegistrationSystem;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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
    private ConcurrentHashMap<String, User> connectedUsers;
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


    private static class Singleton {
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

    public User UserReg(String userName, User user) {
        return registeredUsers.putIfAbsent(userName, user);
    }

    public User login(String username, User user) {
        return connectedUsers.putIfAbsent(username, user);
    }

    public void logOutUser(User user) {
        connectedUsers.remove(user.getName());
    }

    public boolean CourseReg(User user, int courseNum) {
        if (user != null && user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                if (((Student) user).haveAllKdamCourses(course)) {
                    if (course.isAvailable()) {
                        course.getRegisteredStudents().add((Student) user);//todo orderd
                        course.getNumOfCurrStudents().incrementAndGet();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean CourseUnregistered(User user, int courseNum) {//todo add sync
        if (user != null && user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                course.getRegisteredStudents().remove((Student) user);
                course.getNumOfCurrStudents().decrementAndGet();
                return true;
            }
        }
        return false;
    }

    public String getCourseStat(int courseNum) {
        Course course = getCourses().get(courseNum);
        return course.getCourseStat();
    }

    public String getStudentStat(String StudentUserName) {
        User user = getRegisteredUsers().get(StudentUserName);
        if (user instanceof Student) {
            return ((Student) user).getStudentStat();
        }
        return null;//todo check
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
                Course addCourse = new Course(courseName, courseNum);
                courses.put(courseNum, addCourse);

                String kdamCourses = splitString[2];
                String kdamSubst = kdamCourses.substring(1, kdamCourses.length() - 2); //todo check
                String[] str = kdamSubst.split(",");
                List<String> kdamList = new ArrayList<>(Arrays.asList(str));
                addCourse.setKdamCourses((ArrayList) kdamList);


                int numOfMaxStudents = Integer.parseInt(splitString[3]);
                addCourse.setNumOfMaxStudents(numOfMaxStudents);

                //TODO figure out how to save order of courses in file for studentstat
            }
        } catch (Exception e) {
            e.printStackTrace(); // todo return false?}


        }
    }


