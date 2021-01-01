package bgu.spl.net.impl.RegistrationSystem;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private final static String coursesPath ="Courses.txt"; //TODO check if ok like this when running in VM & courses location
    private final ConcurrentHashMap<String, User> registeredUsers = new ConcurrentHashMap<>();
    private final  ConcurrentHashMap<Short, Course> courses = new ConcurrentHashMap<>();//todo needs why concurrent?


    //to prevent user from creating new Database
    private Database() {
        initialize(coursesPath);
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

    public ConcurrentHashMap<Short, Course> getCourses() {
        return courses;
    }

    public User UserReg(String userName, User user) {
        return registeredUsers.putIfAbsent(userName, user);
    }

    public boolean CourseReg(User user, Short courseNum) {
        boolean output=false;
        if (user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                //todo dont sure about snyc think theres no need
                if (((Student) user).haveAllKdamCourses(this,course)) {
                    synchronized (course) {
                        if (course.isAvailable()) {
                            output = course.getRegisteredStudents().add((Student) user);
                            if (output) {
                                course.incrementNumOfCurrStudents();
                                synchronized (user) {
                                    ((Student) user).getRegisteredCourses().add(course);
                                }
                            }
                        }
                    }
                }
            }
        }
        return output;
    }

    public boolean CourseUnregistered(User user, Short courseNum) {//todo add sync
        boolean output = false;
        if (user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                synchronized (course) {
                    output = course.getRegisteredStudents().remove((Student) user);
                    synchronized (user) {
                        ((Student) user).getRegisteredCourses().remove(course);
                    }
                    if (output) {
                        course.decrementNumOfCurrStudents();
                    }
                }
            }
        }
        return output;
    }

    public String getCourseStat(int courseNum) {
        Course course = getCourses().get(courseNum);
        if (course!=null) {
            return course.getCourseStat();
        }
        return null;
    }

    public String getStudentStat(String StudentUserName) {
        User user = getRegisteredUsers().get(StudentUserName);
        if (user instanceof Student) {
            return ((Student) user).getStudentStat();
        }
        return null;//todo check
    }


    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */

    boolean initialize(String coursesFilePath) {
        ArrayList<String> listOfCourses;
        try {
            listOfCourses = (ArrayList<String>)Files.readAllLines(Paths.get(coursesFilePath));
            int line = 1;
            for (String courseLine : listOfCourses) {
                String[] splitString = courseLine.split("\\|");
                Short courseNum = Short.parseShort(splitString[0]);
                String courseName = splitString[1];
                Course course = new Course(line, courseName, courseNum);
                line++;
                courses.putIfAbsent(courseNum, course);//todo chang to put?
                int numOfMaxStudents = Integer.parseInt(splitString[3]);
                course.setNumOfMaxStudents(numOfMaxStudents);
            }
            for (String courseLine : listOfCourses){
                String[] splitString = courseLine.split("\\|");
                Short courseNum = Short.parseShort(splitString[0]);
                Course course = courses.get(courseNum);
                String KdamCoursesList = splitString[2];
                //we check if the kdamCourses not empty list of "[]"
                if(KdamCoursesList.length() > 2) {
                    String kdamSubst = KdamCoursesList.substring(1, KdamCoursesList.length() - 1);//TODO CHANGE THE LIST TO COURSES
                    String[] KdamArray = kdamSubst.split(",");
                    for (String numOfKdam:KdamArray){
                        Course addCourse = courses.get(Short.valueOf(numOfKdam));
                        course.getKdamCourses().add(addCourse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }
}


