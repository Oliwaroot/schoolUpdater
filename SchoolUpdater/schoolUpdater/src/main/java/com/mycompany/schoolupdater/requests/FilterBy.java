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
public class FilterBy {
    private Integer id;

    public FilterBy(Integer id) {
        this.id = id;
    }

    public FilterBy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
