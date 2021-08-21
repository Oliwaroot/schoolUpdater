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
public class StudenAddRequest {
    
    private String name;
    private Integer course;
    
    public StudenAddRequest(){}

    public StudenAddRequest(String name, Integer course) {
        this.name = name;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }
    
    
}
