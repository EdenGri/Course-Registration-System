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
    private final  ConcurrentHashMap<Integer, Course> courses = new ConcurrentHashMap<>();//todo needs why concurrent?


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

    public ConcurrentHashMap<Integer, Course> getCourses() {
        return courses;
    }

    public User UserReg(String userName, User user) {
        return registeredUsers.putIfAbsent(userName, user);
    }

    public boolean CourseReg(User user, int courseNum) {
        boolean output=false;
        if (user != null && user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                //todo dont sure about snyc think theres no need
                if (((Student) user).haveAllKdamCourses(this,course)) {
                    //todo add sync
                    if (course.isAvailable()) {
                        output = course.getRegisteredStudents().add((Student) user);
                        if (output) {
                            course.getNumOfCurrStudents().incrementAndGet();
                            //todo until here
                            ((Student) user).getRegisteredCourses().add(course);
                        }
                    }
                }
            }
        }
        return output;
    }

    public boolean CourseUnregistered(User user, int courseNum) {//todo add sync
        boolean output = false;
        if (user != null && user instanceof Student) {
            Course course = courses.get(courseNum);
            if (course != null) {
                output = course.getRegisteredStudents().remove((Student) user);
                if (output) {
                    course.getNumOfCurrStudents().decrementAndGet();
                }
            }
        }
        return output;
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


    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */

    boolean initialize(String coursesFilePath) {
        ArrayList<String> listOfCourses;
        try {
            listOfCourses = (ArrayList<String>)Files.readAllLines(Paths.get(coursesFilePath));
            int line = 1;
            for (String course : listOfCourses) {
                String[] splitString = course.split("\\|");
                int courseNum = Integer.parseInt(splitString[0]);
                String courseName = splitString[1];
                Course addCourse = new Course(line, courseName, courseNum);
                line++;
                courses.putIfAbsent(courseNum, addCourse);//todo chang to put?

                String kdamCourses = splitString[2];
                //we check if the kdamCourses not empty list of "[]"
                if(kdamCourses.length() > 2) {
                    String kdamSubst = kdamCourses.substring(1, kdamCourses.length() - 1);
                    String[] str = kdamSubst.split(",");
                    List<String> kdamList = new ArrayList<>(Arrays.asList(str));
                    addCourse.setKdamCoursesList((ArrayList)kdamList);
                }

                int numOfMaxStudents = Integer.parseInt(splitString[3]);
                addCourse.setNumOfMaxStudents(numOfMaxStudents);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }
}


