package com.example.schoolupdaterapp.menu.courseactions;

import java.util.ArrayList;

public class CourseResponseMethod {

    private ArrayList<GetCoursesDataModel> Courses;

    public ArrayList<GetCoursesDataModel> getCourses() {
        return Courses;
    }

    public void setCourses(ArrayList<GetCoursesDataModel> courses) {
        Courses = courses;
    }

    public CourseResponseMethod(ArrayList<GetCoursesDataModel> courses) {
        Courses = courses;
    }
}
