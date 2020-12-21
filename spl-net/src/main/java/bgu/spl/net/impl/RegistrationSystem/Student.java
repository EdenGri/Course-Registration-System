package bgu.spl.net.impl.RegistrationSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Student extends User {
    private HashMap<Integer, Course> registeredCourses; //todo check if we need concurrency

    public Student(String username,String password) {
        super(username, password);
        registeredCourses = new HashMap<>();
    }

    public boolean registerCourse(int courseNum){
        //todo check if there is room and etc to register student
    }

    public Set<Integer> getRegisteredCourses() {
        return registeredCourses.keySet();

    }
}
