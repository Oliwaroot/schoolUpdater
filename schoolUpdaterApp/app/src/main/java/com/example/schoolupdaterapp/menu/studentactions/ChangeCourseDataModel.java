package com.example.schoolupdaterapp.menu.studentactions;

public class ChangeCourseDataModel {
    private Integer id;
    private String course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public ChangeCourseDataModel(Integer id, String course) {
        this.id = id;
        this.course = course;
    }

    public ChangeCourseDataModel() {
    }


}
