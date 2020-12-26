package bgu.spl.net.impl.RegistrationSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Course {
    private int serialNum;
    private String courseName;
    private int courseNum;
    private SortedSet<Course> kdamCourses;
    private SortedSet<Student> registeredStudents;
    private int numOfMaxStudents;
    private AtomicInteger numOfCurrStudents;//todo check

    public Course (int serialNum,String courseName, int courseNum){
        this.serialNum = serialNum;
        this.courseName = courseName;
        this.courseNum = courseNum;
        Comparator<Student> comp1 = (Student s1, Student s2) -> (s1.compareTo(s2));
        registeredStudents=new TreeSet<>(comp1);
        Comparator<Course> comp2 = (Course c1, Course c2) -> (c1.compareTo(c2));
        kdamCourses=new TreeSet<>(comp2);
    }

    public int getSerialNum(){
        return serialNum;
    }

    public String getCourseName(){
        return courseName;
    }

    public boolean isAvailable(){//todo need sync?
        return numOfMaxStudents-numOfCurrStudents.get()>0;
    }

    public AtomicInteger getNumOfCurrStudents() {
        return numOfCurrStudents;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public void setNumOfCurrStudents(AtomicInteger numOfCurrStudents) {//todo needed?
        this.numOfCurrStudents = numOfCurrStudents;
    }

    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }

    public SortedSet<Course> getKdamCourses(){
        return kdamCourses;
    }

    public SortedSet<Student> getRegisteredStudents(){
        return registeredStudents;
    }


    public void setKdamCourses(SortedSet<Course> kdamCourses) {
        this.kdamCourses = kdamCourses;
    }

    public String getCourseStat(){
        String courseStat="Course:("+courseNum+")"+courseName+"\n"+
                "Seats Available:"+getNumOfCurrStudents()+"/"+getNumOfMaxStudents()+"\n"+
                "Students Registered:"+getRegisteredStudents();
        return courseStat;
    }

    public boolean isRegistered(User user){
        return registeredStudents.contains(user);
    }

    public int compareTo(Course c2) {
        return serialNum-c2.getSerialNum();
    }

    public int getCourseNum() {
        return courseNum;
    }
}
