package com.example.schoolupdaterapp.menu.courseactions;

public class EditCourseDataModel {
    private String name;
    private Integer id;
    private Integer institution;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstitution() {
        return institution;
    }

    public void setInstitution(Integer institution) {
        this.institution = institution;
    }

    public EditCourseDataModel(String name, Integer id, Integer institution) {
        this.name = name;
        this.id = id;
        this.institution = institution;
    }

    public EditCourseDataModel() {
    }
}
