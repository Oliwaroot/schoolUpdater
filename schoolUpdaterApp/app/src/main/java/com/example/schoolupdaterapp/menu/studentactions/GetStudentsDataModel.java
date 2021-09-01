package com.example.schoolupdaterapp.menu.studentactions;

public class GetStudentsDataModel {
    private String name;
    private String id;
    private String course;
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    private String institution;

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public GetStudentsDataModel(String name, String id, String course, String institution, String courseName) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.courseName = courseName;
        this.institution =institution;
    }

    public GetStudentsDataModel() {
    }
}
