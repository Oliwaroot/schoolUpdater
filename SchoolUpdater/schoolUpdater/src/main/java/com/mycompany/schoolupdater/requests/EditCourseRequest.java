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
public class EditCourseRequest {
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

    public EditCourseRequest(String name, Integer id, Integer institution) {
        this.name = name;
        this.id = id;
        this.institution = institution;
    }

    public EditCourseRequest() {
    }
    
}
