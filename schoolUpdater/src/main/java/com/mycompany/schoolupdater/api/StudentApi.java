/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.api;

import com.mycompany.schoolupdater.ejb.StudentBean;
import com.mycompany.schoolupdater.requests.ChangeCourseRequest;
import com.mycompany.schoolupdater.requests.ChangeInstitutionRequest;
import com.mycompany.schoolupdater.requests.DeleteStudentRequest;
import com.mycompany.schoolupdater.requests.FilterBy;
import com.mycompany.schoolupdater.requests.StudenAddRequest;
import com.mycompany.schoolupdater.requests.StudentEditRequest;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author OliwaPC
 */

@Stateless
@Path("/student")
@Produces({MediaType.APPLICATION_JSON})
public class StudentApi {
    
    @EJB
    StudentBean studentBean;
    
    @GET
    public Response getAllStudents(){
        return studentBean.getAllStudents();
    }
    
    @PUT
    public Response editStudent(StudentEditRequest studentEditRequest) {
        return studentBean.editStudent(studentEditRequest);
    }
    
    @POST
    public Response addStudent(StudenAddRequest studentRequest){
        return studentBean.addStudent(studentRequest);
    }
    
    @Path("/bycourse")
    @POST
    public Response getStudentByCourse(FilterBy getStudentByCourse){
        return studentBean.getStudents_ByCourse(getStudentByCourse);
    }
    
    @Path("/changeCourse")
    @POST
    public Response changeStudentCourse(ChangeCourseRequest changeCourseRequest){
        return studentBean.changeStudentCourse(changeCourseRequest);
    }
    
    @Path("/changeCourseInstitution")
    @POST
    public Response changeStudentCourseINstitution(ChangeInstitutionRequest changeCourseRequest){
        return studentBean.changeStudentCourseInstitution(changeCourseRequest);
    }
    
    @Path("/deleteStudent")
    @POST
    public Response deleteStudent(DeleteStudentRequest deleteStudentRequest) {
        return studentBean.deleteStudent(deleteStudentRequest);
    }
    
}
