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
import com.mycompany.schoolupdater.entities.Institutions;
import com.mycompany.schoolupdater.entities.Students;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import com.mycompany.schoolupdater.requests.ChangeCourseRequest;
import com.mycompany.schoolupdater.requests.ChangeInstitutionRequest;
import com.mycompany.schoolupdater.requests.DeleteStudentRequest;
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

    public Response deleteStudent(DeleteStudentRequest deleteStudentRequest) {
        try {
            if (deleteStudentRequest == null || deleteStudentRequest.getId() == null) {
                throw new BadRequestException("Id is empty");
            }
            Students student = studentDatatbaseBean.getStudent_ById(deleteStudentRequest.getId());
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
    
    public Response changeStudentCourse(ChangeCourseRequest changeCourseRequest){
        try {
            if (changeCourseRequest == null) {
                throw new BadRequestException("Change Course Request is empty");
            }
            Integer studentId =  changeCourseRequest.getId();
            String courseName = changeCourseRequest.getCourse();
            
            Students existingStudent = studentDatatbaseBean.getStudent_ById(studentId);
            Courses oldCourse = courseDatabaseBean.getCourse_ById(existingStudent.getCourse().getId());
            Courses existingCourse = courseDatabaseBean.getCourses_ByNameAndIns(courseName, existingStudent.getCourse().getInstitution().getId());
            
            if(existingStudent == null){
                throw new BadRequestException("Student does not exist.");
            }
            
            if(existingCourse == null){
               throw new BadRequestException("Course does not exist.");
            }
            
            if(oldCourse == existingCourse){
                throw new BadRequestException("Cannot transfer to the same course");
            }
            
            if(existingCourse.getInstitution() != oldCourse.getInstitution()){
                throw new BadRequestException("Cannot transfer student outside institution.");
            } 
            
            existingStudent.setCourse(existingCourse);
            
            if (!transactionProvider.updateEntity(existingStudent)) {
                throw new PersistenceException("Student has been not been deleted");
            }
            
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Student has been transfered to a new course.");
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred.").build();
        }
    }
    
    public Response changeStudentCourseInstitution(ChangeInstitutionRequest changeCourseRequest){
        try {
            if (changeCourseRequest == null) {
                throw new BadRequestException("Change Instittion Request is empty");
            }
            Integer studentId =  changeCourseRequest.getId();
            String courseName = changeCourseRequest.getCourse();
            String institutionName = changeCourseRequest.getInstitution();
            
            Institutions newInstitution = institutionDatabaseBean.getInstitution_ByName(institutionName);
            
            Students existingStudent = studentDatatbaseBean.getStudent_ById(studentId);
            Courses oldCourse = courseDatabaseBean.getCourse_ById(existingStudent.getCourse().getId());
            Courses existingCourse = courseDatabaseBean.getCourses_ByNameAndIns(courseName, newInstitution.getId());
            
            if(existingStudent == null){
                throw new BadRequestException("Student does not exist.");
            }
            
            if(existingCourse == null){
               throw new BadRequestException("Course does not exist.");
            }
            
            if(existingCourse.getInstitution() == oldCourse.getInstitution()){
                throw new BadRequestException("Transfer is to be out of the institution.");
            } 
            
            existingStudent.setCourse(existingCourse);
            
            if (!transactionProvider.updateEntity(existingStudent)) {
                throw new PersistenceException("Student has been not been deleted");
            }
            
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Student has been transfered to a new course in a new institution.");
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred.").build();
        }
    }

    public Response addStudent(StudenAddRequest studentRequest) {
        try {
            if (studentRequest == null) {
                throw new BadRequestException("Student is empty");
            }
            String studentName = studentRequest.getName();
            String courseName = studentRequest.getCourse();
            String institutionName = studentRequest.getInstitution();
            
            Institutions existingInstitution = institutionDatabaseBean.getInstitution_ByName(institutionName);
            if(existingInstitution == null){
               throw new BadRequestException("Course does not exist.");
            }
            
            Courses existingCourse = courseDatabaseBean.getCourses_ByNameAndIns(courseName, existingInstitution.getId());
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
                    courseHashMap.put("id", student.getId());
                    courseHashMap.put("course", student.getCourse().getId().toString());
                    courseHashMap.put("courseName", student.getCourse().getCourseName());
                    courseHashMap.put("institution", student.getCourse().getInstitution().getInstitutionName());
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
