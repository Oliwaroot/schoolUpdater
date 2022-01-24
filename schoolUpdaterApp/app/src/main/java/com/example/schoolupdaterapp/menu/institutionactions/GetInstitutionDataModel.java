package com.example.schoolupdaterapp.menu.institutionactions;

public class GetInstitutionDataModel {
    private String name;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetInstitutionDataModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

//    public DataModel(String name) {
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetInstitutionDataModel() {
    }
}
