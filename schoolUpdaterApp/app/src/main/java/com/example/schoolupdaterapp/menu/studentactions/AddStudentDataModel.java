package com.example.schoolupdaterapp.menu.studentactions;

public class AddStudentDataModel {
    private String name;
    private String course;
    private String institution;

    public AddStudentDataModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public AddStudentDataModel(String name, String course, String institution) {
        this.name = name;
        this.course = course;
        this.institution = institution;
    }
}
