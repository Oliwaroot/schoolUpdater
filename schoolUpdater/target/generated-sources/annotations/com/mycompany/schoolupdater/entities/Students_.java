package com.mycompany.schoolupdater.entities;

import com.mycompany.schoolupdater.entities.Courses;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-08-20T13:05:39")
@StaticMetamodel(Students.class)
public class Students_ { 

    public static volatile SingularAttribute<Students, String> studentName;
    public static volatile SingularAttribute<Students, Courses> course;
    public static volatile SingularAttribute<Students, Integer> id;

}