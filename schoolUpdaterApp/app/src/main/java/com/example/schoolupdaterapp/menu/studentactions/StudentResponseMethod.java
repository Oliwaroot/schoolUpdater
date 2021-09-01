package com.example.schoolupdaterapp.menu.studentactions;

import java.util.ArrayList;

public class StudentResponseMethod {
    private ArrayList<GetStudentsDataModel> Students;

    public StudentResponseMethod(ArrayList<GetStudentsDataModel> students) {
        Students = students;
    }

    public ArrayList<GetStudentsDataModel> getStudents() {
        return Students;
    }

    public void setStudents(ArrayList<GetStudentsDataModel> students) {
        Students = students;
    }
}
