package bgu.spl.net.impl.RegistrationSystem;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Student extends User {
    private ArrayList<Integer> registeredCourses; //todo check if we need concurrency

    public Student(String username,String password) {
        super(username, password);
        registeredCourses = new ArrayList<>();
    }

    public boolean registerCourse(int courseNum){
        return false;//todo check if there is room and etc to register student
    }

    public Set<Integer> getRegisteredCourses() {
        return registeredCourses.; //todo

    }
    public boolean haveAllKdamCourses(Course course){
        return false;
    }

}
