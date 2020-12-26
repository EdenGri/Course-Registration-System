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

    public boolean registerCourse(int courseNum){
        return false;//todo check if there is room and etc to register student
    }

    public SortedSet<Course> getRegisteredCourses() {
        return registeredCourses; //todo
    }
    public boolean haveAllKdamCourses(Course course){
        return false;
    }
    public String getStudentStat(){
        return "Student:"+username+"\n"+
                "Courses:"+getRegisteredCourses();
    }

    public int compareTo(Student s2) {
        return this.username.compareTo(s2.getName());
    }
}
