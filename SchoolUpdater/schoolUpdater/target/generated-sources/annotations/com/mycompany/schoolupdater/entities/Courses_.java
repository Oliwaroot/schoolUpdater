package com.mycompany.schoolupdater.entities;

import com.mycompany.schoolupdater.entities.Institutions;
import com.mycompany.schoolupdater.entities.Students;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-08-22T09:39:27")
@StaticMetamodel(Courses.class)
public class Courses_ { 

    public static volatile SingularAttribute<Courses, Institutions> institution;
    public static volatile SingularAttribute<Courses, String> courseName;
    public static volatile SingularAttribute<Courses, Integer> id;
    public static volatile ListAttribute<Courses, Students> studentsList;

}