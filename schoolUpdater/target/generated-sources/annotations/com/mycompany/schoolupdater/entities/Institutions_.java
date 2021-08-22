package com.mycompany.schoolupdater.entities;

import com.mycompany.schoolupdater.entities.Courses;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-08-22T09:39:27")
@StaticMetamodel(Institutions.class)
public class Institutions_ { 

    public static volatile ListAttribute<Institutions, Courses> coursesList;
    public static volatile SingularAttribute<Institutions, String> institutionName;
    public static volatile SingularAttribute<Institutions, Integer> id;

}