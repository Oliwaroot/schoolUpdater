package com.example.schoolupdaterapp.menu.courseactions;

public class GetCoursesDataModel {

    private String name;
    private String id;
    private String institution;
    private String institutionName;

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

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public GetCoursesDataModel(String name, String id, String institution, String institutionName) {
        this.name = name;
        this.id = id;
        this.institution = institution;
        this.institutionName = institutionName;
    }

    public GetCoursesDataModel() {
    }
}
