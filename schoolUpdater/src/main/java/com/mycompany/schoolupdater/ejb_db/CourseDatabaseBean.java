/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb_db;

import com.mycompany.schoolupdater.entities.Courses;
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
public class CourseDatabaseBean {
    
    @EJB
    TransactionProvider transactionProvider;
    
        public List<Courses> getAllCourses(){
        List courseList = new ArrayList();
       try{
            EntityManager em = transactionProvider.getEM();
            Query query = em.createQuery("SELECT i FROM Courses i");
            
            courseList = transactionProvider.getManyFromQuery(query);
            
            return courseList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return courseList;
        }
    }
        public Courses getCourse_ById(Integer courseId) {
        Courses course = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT c FROM Courses c WHERE c.id = :courseId");
            query.setParameter("courseId", courseId);
            course = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return course;
        }
    }
          public Courses getCourses_ByName(String courseName) {
            Courses course = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT c FROM Courses c WHERE c.courseName = :courseName");
            query.setParameter("courseName", courseName);
            course = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return course;
        }
    }
          
          public Courses getCourses_ByNameAndIns(String courseName, Integer institutionId) {
            Courses course = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT c FROM Courses c WHERE c.courseName = :courseName AND c.institution.id = :institutionId");
            query.setParameter("institutionId", institutionId);
            query.setParameter("courseName", courseName);
            course = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return course;
        }
    }
          
          public List<Courses> getCoursesByInstitution(Integer institutionId){
            List courseList = new ArrayList();
            
            try{        
                EntityManager em = transactionProvider.getEM();
                Query query = em.createQuery("SELECT c FROM Courses c WHERE c.institution.id = :institutionId");
                query.setParameter("institutionId", institutionId);
                courseList = transactionProvider.getManyFromQuery(query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                return courseList;
            }
        }
    
}
