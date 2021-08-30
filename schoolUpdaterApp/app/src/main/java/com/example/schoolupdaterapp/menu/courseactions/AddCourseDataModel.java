package com.example.schoolupdaterapp.menu.courseactions;

public class AddCourseDataModel {

    private String name;
    private String institution;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public AddCourseDataModel(String name, String institution) {
        this.name = name;
        this.institution = institution;
    }

    public AddCourseDataModel() {
    }
}
