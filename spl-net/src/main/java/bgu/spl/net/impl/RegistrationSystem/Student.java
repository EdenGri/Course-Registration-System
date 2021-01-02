package bgu.spl.net.impl.RegistrationSystem;

import java.util.*;

public class Student extends User {
    private SortedSet<Course> registeredCourses;

    public Student(String username, String password) {
        super(username, password);
        Comparator<Course> comp = (Course c1, Course c2) -> (c1.compareTo(c2));
        registeredCourses = new TreeSet<>(comp);
    }

    //Returns the courses the student is registered to
    public SortedSet<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    //Returns true if the student has all the kdam courses that are required for the specific course
    public boolean haveAllKdamCourses(Course course) {
        SortedSet<Course> KdamCourses = course.getKdamCourses();
        for (Course kdam : KdamCourses) {
            if (!registeredCourses.contains(kdam)) {
                return false;
            }
        }
        return true;
    }

    //Returns the student's status
    //added the sync in case of parallelism between studentStat and courseReg/courseUnreg
    public synchronized String getStudentStat() {
        return "Student: " + username + "\n" +
                "Courses: " + getRegisteredCoursesToString();
    }

    public String getRegisteredCoursesToString() {
        String output = "[";
        for (Course course : registeredCourses) {
            output = output.concat(course.getCourseNum() + ",");
        }
        if (output.length() > 1) {//if there is an extra ","
            output = output.substring(0, output.length() - 1);//remove the last ","
        }
        output = output.concat("]");
        return output;
    }

    //compares students by alphabetical order
    public int compareTo(Student s2) {
        return this.username.compareTo(s2.getName());
    }

    //adds the course to the registered courses
    public void add(Course course) {
        registeredCourses.add(course);
    }
}
