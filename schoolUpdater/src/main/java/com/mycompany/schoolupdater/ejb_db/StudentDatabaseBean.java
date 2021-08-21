/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb_db;

import com.mycompany.schoolupdater.entities.Students;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author OliwaPC
 */

@Stateless
public class StudentDatabaseBean {
    
    @EJB
    TransactionProvider transactionProvider;
    
    @EJB
    CourseDatabaseBean courseDatabaseBean;
    
        public List<Students> getAllStudents(){
        List studentList = new ArrayList();
       try{
            EntityManager em = transactionProvider.getEM();
            Query query = em.createQuery("SELECT i FROM Students i");
            
            studentList = transactionProvider.getManyFromQuery(query);
            
            return studentList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return studentList;
        }
    }
       
        public List<Students> getStudentsByCourse(Integer courseId){
            List studentList = new ArrayList();
            
            try{        
                EntityManager em = transactionProvider.getEM();
                Query query = em.createQuery("SELECT i FROM Students i WHERE i.course.id = :courseId");
                query.setParameter("courseId", courseId);
                studentList = transactionProvider.getManyFromQuery(query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                return studentList;
            }
        }
        
        public Students getStudent_ById(Integer studentId) {
        Students student = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT s FROM Students s WHERE s.id = :studentId");
            query.setParameter("studentId", studentId);
            student = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return student;
        }
    }
         public Students getStudent_ByName(String studentName) {
            Students student = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT s FROM Students s WHERE s.studentName = :studentName");
            query.setParameter("studentName", studentName);
            student = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return student;
        }
    }
    
}
