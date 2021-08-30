package com.example.schoolupdaterapp.menu.studentactions;

public class EditStudentDataModel {

    private Integer id;
    private String name;
    private Integer course;

    public EditStudentDataModel(Integer id, String name, Integer course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public EditStudentDataModel() {
    }
}
