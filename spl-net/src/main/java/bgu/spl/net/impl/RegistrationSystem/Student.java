package bgu.spl.net.impl.RegistrationSystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Student extends User {
    private SortedSet<Course> registeredCourses;//todo check if we need concurrency

    public Student(String username,String password) {
        super(username, password);
        Comparator<Course> comp = (Course c1, Course c2) -> (c1.compareTo(c2));
        registeredCourses=new TreeSet<>(comp);
    }

    public SortedSet<Course> getRegisteredCourses() {
        return registeredCourses; //todo need?
    }
    public boolean haveAllKdamCourses(Course course){//todo
        return false;
    }
    public String getStudentStat(){
        return "Student:"+username+"\n"+
                "Courses:"+getRegisteredCoursesToString();
    }

    public String getRegisteredCoursesToString() {
        String output="[";
        for (Course course: registeredCourses){
            output.concat(course.getCourseNum()+",");
        }
        output.substring(0,output.length()-1);//remove the last ","
        output.concat("]");
        return output;
    }

    public int compareTo(Student s2) {
        return this.username.compareTo(s2.getName());
    }
}
