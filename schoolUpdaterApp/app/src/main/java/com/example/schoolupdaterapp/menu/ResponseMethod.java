package com.example.schoolupdaterapp.menu;

import java.util.ArrayList;

public class ResponseMethod {

    private ArrayList<DataModel> Institutions;

    public ArrayList<DataModel> getResponseMethods() {
        return Institutions;
    }

    public void setResponseMethods(ArrayList<DataModel> responseMethods) {
        this.Institutions= responseMethods;
    }

    public ResponseMethod(ArrayList<DataModel> responseMethods) {
        this.Institutions = responseMethods;
    }
}
