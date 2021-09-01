/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.requests;

/**
 *
 * @author OliwaPC
 */
public class AddCourseRequest {
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

    public AddCourseRequest(String name, String institution) {
        this.name = name;
        this.institution = institution;
    }

    public AddCourseRequest() {
    }
}
