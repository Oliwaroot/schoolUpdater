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
    private Integer institution;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInstitution() {
        return institution;
    }

    public void setInstitution(Integer institution) {
        this.institution = institution;
    }

    public AddCourseRequest(String name, Integer institution) {
        this.name = name;
        this.institution = institution;
    }

    public AddCourseRequest() {
    }
}
