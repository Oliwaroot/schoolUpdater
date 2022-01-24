package com.example.schoolupdaterapp.menu.institutionactions;

import java.util.ArrayList;

public class InstitutionResponseMethod {

    private ArrayList<GetInstitutionDataModel> Institutions;

    public ArrayList<GetInstitutionDataModel> getResponseMethods() {
        return Institutions;
    }

    public void setResponseMethods(ArrayList<GetInstitutionDataModel> responseMethods) {
        this.Institutions= responseMethods;
    }

    public InstitutionResponseMethod(ArrayList<GetInstitutionDataModel> responseMethods) {
        this.Institutions = responseMethods;
    }
}
