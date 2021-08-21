/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.api;

import com.mycompany.schoolupdater.ejb.CourseBean;
import com.mycompany.schoolupdater.requests.AddCourseRequest;
import com.mycompany.schoolupdater.requests.EditCourseRequest;
import com.mycompany.schoolupdater.requests.FilterBy;
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
@Path("/course")
@Produces({MediaType.APPLICATION_JSON})
public class CourseApi {
    @EJB
    CourseBean courseBean;
    
    @GET
    public Response getAllCourses(){
        return courseBean.getAllCourses();
    }
    
    @PUT
    public Response editCourse(EditCourseRequest editCourseRequest) {
        return courseBean.editCourse(editCourseRequest);
    }
    
    @POST
    public Response addCourse(AddCourseRequest addCourseRequest){
        return courseBean.addCourse(addCourseRequest);
    }

    @DELETE
    public Response deleteCourse(@QueryParam("course_id")Integer courseId) {
        return courseBean.deleteCourse(courseId);
    }
    
    @Path("/byInstitution")
    @POST
    public Response getCourseByInstitution(FilterBy getCoursesByInstitution){
        return courseBean.getCourses_ByInstitution(getCoursesByInstitution);
    }
}
