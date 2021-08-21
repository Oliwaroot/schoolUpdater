/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb;

import com.mycompany.schoolupdater.ejb_db.CourseDatabaseBean;
import com.mycompany.schoolupdater.ejb_db.InstitutionDatabaseBean;
import com.mycompany.schoolupdater.entities.Courses;
import com.mycompany.schoolupdater.entities.Institutions;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import com.mycompany.schoolupdater.requests.AddCourseRequest;
import com.mycompany.schoolupdater.requests.EditCourseRequest;
import com.mycompany.schoolupdater.requests.FilterBy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

/**
 *
 * @author OliwaPC
 */

@Stateless
public class CourseBean {
    @EJB
    TransactionProvider transactionProvider;
    
    @EJB
    InstitutionDatabaseBean institutionDatabaseBean;  
    
    @EJB
    CourseDatabaseBean courseDatabaseBean;
 
    
       public Response deleteCourse(Integer courseId) {
        try {
            if (courseId == null) {
                throw new BadRequestException("Id is empty");
            }
            Courses course = courseDatabaseBean.getCourse_ById(courseId);
            if(course == null){
                throw new BadRequestException("Course does not exist");
            }
            
            if (!transactionProvider.deleteEntity(course)) {
                throw new PersistenceException("Course has been not been deleted. It has students.");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Course has been deleted");
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
       
       public Response addCourse(AddCourseRequest addCourseRequest) {
        try {
            if (addCourseRequest == null) {
                throw new BadRequestException("Course is empty");
            }
            String courseName = addCourseRequest.getName();
            Integer institutionId = addCourseRequest.getInstitution();
            
            Institutions existingInstitution  = institutionDatabaseBean.getInstitution_ById(institutionId);
            if(existingInstitution == null){
               throw new BadRequestException("Institution does not exist.");
            }
            
            Courses existingCourse = courseDatabaseBean.getCourses_ByName(courseName);
            if(existingCourse != null && Objects.equals(existingCourse.getInstitution().getId(), institutionId)){
                throw new BadRequestException("Course already exists.");
            }
            
            Courses course = new Courses();
            course.setCourseName(courseName);
            course.setInstitution(existingInstitution);

            if (!transactionProvider.createEntity(course)) {
                throw new PersistenceException("Course has not been saved");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Course " + course.getCourseName() + " has been created");
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
       
       public Response editCourse(EditCourseRequest editCourseRequest) {
        try {
            if (editCourseRequest == null) {
                throw new BadRequestException("Course is empty");
            }
            Integer courseId = editCourseRequest.getId();
            String courseName = editCourseRequest.getName();
            Integer institutionId = editCourseRequest.getInstitution();
            
            Courses existingCourse2 = courseDatabaseBean.getCourse_ById(courseId);
            if(existingCourse2 == null){
                throw new BadRequestException("Course does not exist.");
            }
            
            Institutions existingInstitution  = institutionDatabaseBean.getInstitution_ById(institutionId);
            if(existingInstitution == null){
               throw new BadRequestException("Institution does not exist.");
            }
            
            Courses existingCourse = courseDatabaseBean.getCourses_ByName(courseName);
            if(existingCourse != null && Objects.equals(existingCourse.getInstitution().getId(), institutionId)){
                throw new BadRequestException("Course already exists.");
            }
            if(existingCourse2.getInstitution() != existingInstitution){
                throw new BadRequestException("Institution does not match.");
            }
            
            Courses course = new Courses();
            course.setId(courseId);
            course.setCourseName(courseName);
            course.setInstitution(existingInstitution);

            if (!transactionProvider.updateEntity(course)) {
                throw new PersistenceException("Course has not been updated.");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Course " + course.getCourseName() + " has been updated.");
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
    
    public Response getAllCourses(){
        try{
            List<Courses> courseList = courseDatabaseBean.getAllCourses();
            HashMap<String, Object> res = new HashMap();
            if(courseList.isEmpty()){
                res.put("message", "No Courses Exist");
            }
            else {
                List courses = new ArrayList();
                for(Courses course : courseList){
                    HashMap<String, Object> courseHashMap = new HashMap();
                    courseHashMap.put("name", course.getCourseName());
                    courseHashMap.put("institution", course.getInstitution().getInstitutionName());
                    courses.add(courseHashMap);
                }
                res.put("Courses", courses);
            }
              return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }
    
    public Response getCourses_ByInstitution(FilterBy getCoursesByInstitution){
            try {
                        if(getCoursesByInstitution == null){
            throw new BadRequestException("No institution provided.");
        }
        Integer institutionId = getCoursesByInstitution.getId();
               Institutions institution = institutionDatabaseBean.getInstitution_ById(institutionId);
                if(institution == null){
                    throw new BadRequestException("Institution Does not exist.");
                }
            List<Courses> courseList = courseDatabaseBean.getCoursesByInstitution(institutionId);
            HashMap<String, Object> res = new HashMap();
            if (courseList.isEmpty()) {
                res.put("message", "No courses exist in that institution.");
            } else {
                List courses = new ArrayList();
                for (Courses course : courseList) {               
                    HashMap<String, Object> courseHashMap = new HashMap();
                    courseHashMap.put("name", course.getCourseName());
                    courseHashMap.put("course", course.getInstitution().getInstitutionName());
                    courses.add(courseHashMap);
                }
                res.put("Students", courses);
            }
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    } 
   
}
