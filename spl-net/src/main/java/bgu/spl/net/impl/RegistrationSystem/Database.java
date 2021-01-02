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
    private final static String coursesPath = "./Courses.txt"; //TODO check if ok like this when running in VM & courses location
    private final ConcurrentHashMap<String, User> registeredUsers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Short, Course> courses = new ConcurrentHashMap<>();


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

    //Returns the registered users
    public ConcurrentHashMap<String, User> getRegisteredUsers() {
        return registeredUsers;
    }

    //Returns the courses
    public ConcurrentHashMap<Short, Course> getCourses() {
        return courses;
    }

    //registers a user to the service
    public User UserReg(String userName, User user) {
        //indicates if the user don't registered yet
        return registeredUsers.putIfAbsent(userName, user);
    }

    //registers a user to the course with the specific number
    public boolean CourseReg(User user, Short courseNum) {
        boolean output = false;
        if (user instanceof Student && user.getIsLoggedIn()) {
            Course course = courses.get(courseNum);
            //checks if the course exists and if the user has all kdam courses
            if (course != null && ((Student) user).haveAllKdamCourses(course)) {
                //added the sync in case of parallelism between courseStat and courseReg
                synchronized (course) {
                    //check if there is an empty seat in the course for a user
                    if (course.isAvailable()) {
                        output = course.add((Student) user);
                        //check if the user is not already registered to this course
                        if (output) {
                            course.incrementNumOfCurrStudents();
                            //added the sync in case of parallelism between studentStat and courseReg
                            synchronized (user) {
                                ((Student) user).add(course);
                            }
                        }
                    }
                }
            }
        }
        return output;
    }

    //unregisters user to the course with the specific number
    public boolean CourseUnregistered(User user, Short courseNum) {
        boolean output = false;
        if (user instanceof Student && user.getIsLoggedIn()) {
            Course course = courses.get(courseNum);
            //checks if the course is exists
            if (course != null) {
                //added the sync in case of parallelism between courseStat and courseUnreg
                synchronized (course) {
                    output = course.getRegisteredStudents().remove((Student) user);
                    //added the sync in case of parallelism between studentStat and courseUnreg
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

    //Returns the course status to the course with the specific number
    public String getCourseStat(Short courseNum) {
        Course course = courses.get(courseNum);
        //checks if the course is exists
        if (course != null) {
            return course.getCourseStat();
        }
        return null;
    }

    //Returns the student status to the student with the specific name
    public String getStudentStat(String StudentUserName) {
        User user = getRegisteredUsers().get(StudentUserName);
        if (user instanceof Student) {
            return ((Student) user).getStudentStat();
        }
        return null;
    }


    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */

    boolean initialize(String coursesFilePath) {
        ArrayList<String> listOfCourses;
        try {
            //reads all lines from courses file
            listOfCourses = (ArrayList<String>) Files.readAllLines(Paths.get(coursesFilePath));
            int line = 1;
            for (String courseLine : listOfCourses) {
                String[] splitString = courseLine.split("\\|");
                //saves the course number
                Short courseNum = Short.parseShort(splitString[0]);
                //saves the course name
                String courseName = splitString[1];
                //creates new course object
                Course course = new Course(line, courseName, courseNum);
                line++;
                courses.putIfAbsent(courseNum, course);
                //saves the course's seat limit
                int numOfMaxStudents = Integer.parseInt(splitString[3]);
                course.setNumOfMaxStudents(numOfMaxStudents);
            }
            //saves kdam courses list file according to order in courses file
            for (String courseLine : listOfCourses) {
                String[] splitString = courseLine.split("\\|");
                Short courseNum = Short.parseShort(splitString[0]);
                Course course = courses.get(courseNum);
                String KdamCoursesList = splitString[2];
                //we check if the kdamCourses list is not an empty list of "[]"
                if (KdamCoursesList.length() > 2) {
                    String kdamSubst = KdamCoursesList.substring(1, KdamCoursesList.length() - 1);
                    String[] KdamArray = kdamSubst.split(",");
                    for (String numOfKdam : KdamArray) {
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


