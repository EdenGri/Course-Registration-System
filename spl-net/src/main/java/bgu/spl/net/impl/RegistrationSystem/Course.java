package bgu.spl.net.impl.RegistrationSystem;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private int courseNum;
    private ArrayList<Course> kdamCourses;
    private ArrayList<Student> registeredStudents;
    private int numOfMaxStudents;
    private int numOfCurrStudents;

    public Course (String courseName, int courseNum){
        this.courseName = courseName;
        this.courseNum = courseNum;

        //Todo read this input from courses.txt file?????
    }

    public boolean isAvailable(){
        return numOfMaxStudents-numOfCurrStudents>0;
    }

    public LinkedList<Course> getKdamCourses(){
        return kdamCourses;
    }
}
