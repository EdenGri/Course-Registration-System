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
    private ArrayList<Course> kdamCourses;
    private SortedSet<Student> registeredStudents;
    private int numOfMaxStudents;
    private AtomicInteger numOfCurrStudents;//todo check

    public Course (int serialNum,String courseName, int courseNum){
        this.serialNum = serialNum;
        this.courseName = courseName;
        this.courseNum = courseNum;
        Comparator<Student> comp = (Student s1, Student s2) -> (s1.compareTo(s2));
        registeredStudents=new TreeSet<>(comp);
        //Todo read this input from courses.txt file?????
    }

    public int getSerialNum(){
        return serialNum;
    }

    public String getCourseName(){
        return courseName;
    }

    public boolean isAvailable(){//todo need sync?
        return numOfMaxStudents-numOfCurrStudents>0;
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

    public ArrayList<Course> getKdamCourses(){
        return kdamCourses;
    }

    public ArrayList<Student> getRegisteredStudents(){
        return registeredStudents;
    }


    public void setKdamCourses(ArrayList<Course> kdamCourses) {
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
}
