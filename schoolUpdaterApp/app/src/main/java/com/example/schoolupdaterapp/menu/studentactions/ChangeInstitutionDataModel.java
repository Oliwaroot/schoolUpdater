package com.example.schoolupdaterapp.menu.studentactions;

public class ChangeInstitutionDataModel {

    private Integer id;
    private String course;
    private String institution;

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

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public ChangeInstitutionDataModel(Integer id, String course, String institution) {
        this.id = id;
        this.course = course;
        this.institution = institution;
    }

    public ChangeInstitutionDataModel() {
    }
}
