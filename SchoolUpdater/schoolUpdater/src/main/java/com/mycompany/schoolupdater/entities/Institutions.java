/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OliwaPC
 */
@Entity
@Table(name = "institutions", catalog = "schoolupdater", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institutions.findAll", query = "SELECT i FROM Institutions i")
    , @NamedQuery(name = "Institutions.findById", query = "SELECT i FROM Institutions i WHERE i.id = :id")
    , @NamedQuery(name = "Institutions.findByInstitutionName", query = "SELECT i FROM Institutions i WHERE i.institutionName = :institutionName")})
public class Institutions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "institution_name", length = 255)
    private String institutionName;
    @OneToMany(mappedBy = "institution")
    private List<Courses> coursesList;

    public Institutions() {
    }

    public Institutions(Integer id) {
        this.id = id;
    }
    
    public Institutions(Integer id, String institutionName) {
        this.id = id;
        this.institutionName = institutionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @XmlTransient
    public List<Courses> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<Courses> coursesList) {
        this.coursesList = coursesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institutions)) {
            return false;
        }
        Institutions other = (Institutions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.schoolupdater.entities.Institutions[ id=" + id + " ]";
    }
    
}
