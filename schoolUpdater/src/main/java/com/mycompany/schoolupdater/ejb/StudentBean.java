/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb;

import com.mycompany.schoolupdater.ejb_db.CourseDatabaseBean;
import com.mycompany.schoolupdater.ejb_db.InstitutionDatabaseBean;
import com.mycompany.schoolupdater.ejb_db.StudentDatabaseBean;
import com.mycompany.schoolupdater.entities.Courses;
import com.mycompany.schoolupdater.entities.Students;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import com.mycompany.schoolupdater.requests.FilterBy;
import com.mycompany.schoolupdater.requests.StudenAddRequest;
import com.mycompany.schoolupdater.requests.StudentEditRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class StudentBean {

    @EJB
    TransactionProvider transactionProvider;

    @EJB
    StudentDatabaseBean studentDatatbaseBean;

    @EJB
    CourseDatabaseBean courseDatabaseBean;

    @EJB
    InstitutionDatabaseBean institutionDatabaseBean;

    public Response deleteStudent(Integer studentId) {
        try {
            if (studentId == null) {
                throw new BadRequestException("Id is empty");
            }
            Students student = studentDatatbaseBean.getStudent_ById(studentId);
            if(student == null){
                throw new BadRequestException("Student does not exist");
            }
            if (!transactionProvider.deleteEntity(student)) {
                throw new PersistenceException("Student has been not been deleted");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Student has been deleted");
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

    public Response addStudent(StudenAddRequest studentRequest) {
        try {
            if (studentRequest == null) {
                throw new BadRequestException("Student is empty");
            }
            String studentName = studentRequest.getName();
            Integer courseId = studentRequest.getCourse();
            
            Courses existingCourse = courseDatabaseBean.getCourse_ById(courseId);
            if(existingCourse == null){
               throw new BadRequestException("Course does not exist.");
            }
            
            Students existingStudent = studentDatatbaseBean.getStudent_ByName(studentName);
            if(existingStudent != null){
                throw new BadRequestException("Student already exists.");
            }
            
            Students student = new Students();
            student.setStudentName(studentName);
            student.setCourse(existingCourse);

            if (!transactionProvider.createEntity(student)) {
                throw new PersistenceException("Student has not been saved");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Student " + student.getStudentName() + " has been created");
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

    public Response editStudent(StudentEditRequest studentEditRequest) {
        try {
            if (studentEditRequest == null) {
                throw new BadRequestException("Student is empty.");
            }
            
            Integer studentId = studentEditRequest.getId();
            String studentName = studentEditRequest.getName();
            Integer course = studentEditRequest.getCourse();
            
            Students existingStudent = studentDatatbaseBean.getStudent_ByName(studentName);
            if(existingStudent != null){
                throw new BadRequestException("Student with similar name already exists.");
            }
            
            Students existingStudent2 = studentDatatbaseBean.getStudent_ById(studentId);
            if(existingStudent2 == null){
                throw new BadRequestException("Student does not exist.");
            }
            
            Courses existingCourse = courseDatabaseBean.getCourse_ById(course);
            if(existingCourse == null){
               throw new BadRequestException("Course does not exist.");
            }
            if(existingStudent2.getCourse() != existingCourse){
                throw new BadRequestException("Course does not match.");
            }
            
            Students student = new Students();
            student.setId(studentId);
            student.setStudentName(studentName);
            student.setCourse(existingCourse);

            if (!transactionProvider.updateEntity(student)) {
                throw new PersistenceException("Student has not been updated");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Student " + student.getStudentName() + " has been updated");
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

    public Response getAllStudents() {
        try {
            List<Students> studentList = studentDatatbaseBean.getAllStudents();
            HashMap<String, Object> res = new HashMap();
            if (studentList.isEmpty()) {
                res.put("message", "No Students Exist");
            } else {
                List students = new ArrayList();
                for (Students student : studentList) {
                    HashMap<String, Object> courseHashMap = new HashMap();
                    courseHashMap.put("name", student.getStudentName());
                    courseHashMap.put("course", student.getCourse().getCourseName());
                    students.add(courseHashMap);
                }
                res.put("Students", students);
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
    
    
    public Response getStudents_ByCourse(FilterBy getStudentsByCourse){
            try {
                        if(getStudentsByCourse == null){
            throw new BadRequestException("No course provided.");
        }
        Integer courseId = getStudentsByCourse.getId();
                Courses course = courseDatabaseBean.getCourse_ById(courseId);
                if(course == null){
                    throw new BadRequestException("Course Does not exist.");
                }
            List<Students> studentList = studentDatatbaseBean.getStudentsByCourse(courseId);
            HashMap<String, Object> res = new HashMap();
            if (studentList.isEmpty()) {
                res.put("message", "No students exist in that course.");
            } else {
                List students = new ArrayList();
                for (Students student : studentList) {               
                    HashMap<String, Object> studentHashMap = new HashMap();
                    studentHashMap.put("name", student.getStudentName());
                    studentHashMap.put("course", student.getCourse().getCourseName());
                    students.add(studentHashMap);
                }
                res.put("Students", students);
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
