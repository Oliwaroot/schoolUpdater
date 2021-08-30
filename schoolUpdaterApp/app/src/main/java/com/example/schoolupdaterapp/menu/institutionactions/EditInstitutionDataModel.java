package com.example.schoolupdaterapp.menu.institutionactions;

public class EditInstitutionDataModel {
    private String name;
    private Integer id;
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public EditInstitutionDataModel(String message) {
        Message = message;
    }

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

    public EditInstitutionDataModel(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public EditInstitutionDataModel() {
    }
}
