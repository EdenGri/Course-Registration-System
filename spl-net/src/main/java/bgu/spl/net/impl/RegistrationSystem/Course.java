package bgu.spl.net.impl.RegistrationSystem;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Course {
    private int serialNum;
    private String courseName;
    private Short courseNum;
    private SortedSet<Course> kdamCourses;
    private SortedSet<Student> registeredStudents;
    private int numOfMaxStudents;
    private int numOfCurrStudents;

    public Course (int serialNum,String courseName, Short courseNum){
        this.serialNum = serialNum;
        this.courseName = courseName;
        this.courseNum = courseNum;
        Comparator<Student> comp1 = (Student s1, Student s2) -> (s1.compareTo(s2));
        registeredStudents=new TreeSet<>(comp1);
        Comparator<Course> comp2 = (Course c1, Course c2) -> (c1.compareTo(c2));
        kdamCourses =new TreeSet<>(comp2);
        numOfCurrStudents=0;
    }

    //Returns serial number of the course
    public int getSerialNum(){
        return serialNum;
    }

    //Returns the number of max students in the course
    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    //Returns the number of the course
    public Short getCourseNum() {
        return courseNum;
    }

    //Returns true if there is an empty seat in the course
    public boolean isAvailable(){
        return (numOfMaxStudents-numOfCurrStudents)>0;
    }

    //Returns the number of empty seats in the course
    private int numOfSeatsAvailable(){
        return numOfMaxStudents-numOfCurrStudents;
    }

    //Sets the number of max seats in the course
    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }

    //Returns the kdam courses
    public SortedSet<Course> getKdamCourses(){
        return kdamCourses;
    }

    //Returns the students that are registered to the course
    public SortedSet<Student> getRegisteredStudents(){
        return registeredStudents;
    }

    //Returns the course state
    //added the sync in case of parallelism between courseStat and courseReg/courseUnreg
    public synchronized String getCourseStat(){
        String courseStat="Course: ("+courseNum+") "+courseName+"\n"+
                "Seats Available: "+numOfSeatsAvailable()+"/"+numOfMaxStudents+"\n"+
                "Students Registered: "+getRegisteredStudentsToString();
        return courseStat;
    }

    private String getRegisteredStudentsToString() {
        String output = "[";
        for (Student student:registeredStudents){
            output = output.concat(student.getName()+",");
        }
        if (output.length()>1) {//if the is extra ","
            output = output.substring(0, output.length() - 1);//remove the last ","
        }
       output = output.concat("]");
        return output;
    }

    //Returns true if the user iz registered to the course
    public boolean isRegistered(User user){
        return registeredStudents.contains(user);
    }

    //compares two courses by their serial number
    public int compareTo(Course c2) {
        return serialNum-c2.getSerialNum();
    }

    public String KdamCoursesToString() {
        String output = "[";
        for (Course kdam:kdamCourses){
            output =output.concat(kdam.courseNum.toString());
            output =output.concat(",");
        }
        if (output.length()>1) {//if the is extra ","
            output = output.substring(0, output.length() - 1);//remove the last ","
        }
        output = output.concat("]");
        return output;
    }

    //increments the number of the registered students by one
    public void incrementNumOfCurrStudents() {
        numOfCurrStudents++;
    }

    //decrements the number of the registered students by one
    public void decrementNumOfCurrStudents() {
        numOfCurrStudents--;
    }

    //add student to the registered students by one
    public boolean add(Student student) {
        return registeredStudents.add(student);
    }
}
