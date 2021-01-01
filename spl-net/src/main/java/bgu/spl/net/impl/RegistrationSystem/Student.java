package bgu.spl.net.impl.RegistrationSystem;

import java.util.*;

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

    public boolean haveAllKdamCourses(Database database,Course course){
        SortedSet<Course> KdamCourses = course.getKdamCourses();
        for (Course kdam:KdamCourses){
            if (!registeredCourses.contains(kdam)){
                return false;
            }
        }
        return true;
    }

    public String getStudentStat(){
        return "Student:"+username+"\n"+
                "Courses:"+getRegisteredCoursesToString();
    }
        //TODO eden check all output = something
    public String getRegisteredCoursesToString() {
        String output="[";
        for (Course course: registeredCourses){ //todo shouldnt we check its not empty?
            output = output.concat(course.getCourseNum()+",");
        }
        output = output.substring(0,output.length()-1);//remove the last ","
        output = output.concat("]");
        return output;
    }

    public int compareTo(Student s2) {
        return this.username.compareTo(s2.getName());
    }
}
