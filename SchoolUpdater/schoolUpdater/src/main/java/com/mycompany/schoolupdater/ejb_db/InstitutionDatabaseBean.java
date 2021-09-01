/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb_db;

import com.mycompany.schoolupdater.entities.Institutions;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OliwaPC
 */

@Stateless
public class InstitutionDatabaseBean {
    @EJB
    TransactionProvider transactionProvider;
    
        public List<Institutions> getAllInstitutions(){
        List institutionList = new ArrayList();
       try{
            EntityManager em = transactionProvider.getEM();
            Query query = em.createQuery("SELECT i FROM Institutions i");
            
            institutionList = transactionProvider.getManyFromQuery(query);
            
            return institutionList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return institutionList;
        }
    }
        public Institutions getInstitution_ById(Integer institutionId) {
        Institutions institution = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT i FROM Institutions i WHERE i.id = :institutionId");
            query.setParameter("institutionId", institutionId);
            institution = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return institution;
        }
    }
        public Institutions getInstitution_ByName(String institutionName) {
        Institutions institution = null;
        try {
            EntityManager entityManager = transactionProvider.getEM();
            Query query = entityManager.createQuery("SELECT i FROM Institutions i WHERE i.institutionName = :institutionName");
            query.setParameter("institutionName", institutionName);
            institution = transactionProvider.getSingleResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return institution;
        }
    }
}
