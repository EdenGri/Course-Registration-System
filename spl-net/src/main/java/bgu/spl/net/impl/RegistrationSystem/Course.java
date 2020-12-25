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

    public int getNumOfCurrStudents() {
        return numOfCurrStudents;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public void setNumOfCurrStudents(int numOfCurrStudents) {
        this.numOfCurrStudents = numOfCurrStudents;
    }

    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }

    public ArrayList<Course> getKdamCourses(){
        return kdamCourses;
    }

    public CourseStat courseStat(){
        return new CourseStat()
    }

    public void setKdamCourses(ArrayList<Course> kdamCourses) {
        this.kdamCourses = kdamCourses;
    }
}
