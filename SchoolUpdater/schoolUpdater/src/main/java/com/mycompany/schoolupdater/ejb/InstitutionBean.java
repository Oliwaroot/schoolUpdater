/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.ejb;

import com.mycompany.schoolupdater.ejb_db.InstitutionDatabaseBean;
import com.mycompany.schoolupdater.entities.Institutions;
import com.mycompany.schoolupdater.jpa.TransactionProvider;
import com.mycompany.schoolupdater.requests.AddInstitutionRequest;
import com.mycompany.schoolupdater.requests.DeleteInstitutionRequest;
import com.mycompany.schoolupdater.requests.EditInstitutionRequest;
import com.mycompany.schoolupdater.requests.GetSingleInstRequest;
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
public class InstitutionBean {
    
    @EJB
    TransactionProvider transactionProvider;
    
    @EJB
    InstitutionDatabaseBean institutionDatabaseBean;    
    
       public Response deleteInstitution(DeleteInstitutionRequest deleteInstitutionRequest) {
        try {
            if (deleteInstitutionRequest == null || deleteInstitutionRequest.getId() == null) {
                throw new BadRequestException("Id is empty");
            }
            Institutions institution = institutionDatabaseBean.getInstitution_ById(deleteInstitutionRequest.getId());
            if(institution == null){
                throw new BadRequestException("Institution does not exist.");
            }
            
            if (!transactionProvider.deleteEntity(institution)) {
                throw new PersistenceException("Institution has been not been deleted. It has courses.");
            }
            HashMap<String, Object> res = new HashMap<>();
            res.put("Message", "Institution has been deleted");
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
    
     public Response addInstitution(AddInstitutionRequest addInstitutionRequest) {
        try {
            if (addInstitutionRequest == null || addInstitutionRequest.getName() == null) {
                throw new BadRequestException("Institution is empty");
            }
            String institutionName = addInstitutionRequest.getName();           
            Institutions existingInstitution = institutionDatabaseBean.getInstitution_ByName(institutionName);
            if(existingInstitution != null){
                throw new BadRequestException("Institution already exists");
            }        
            Institutions institution = new Institutions();
            institution.setInstitutionName(institutionName);
            
            if (!transactionProvider.createEntity(institution)) {
                throw new PersistenceException("Institution has not been saved");
            }   
            HashMap<String, Object> res = new HashMap<>();
            res.put("name", "Institution " + institution.getInstitutionName() + " has been created");
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
     
    public Response editInstitution(EditInstitutionRequest editInstitutionRequest) {
        HashMap<String, Object> res = new HashMap<>();
        try {
           
                if(editInstitutionRequest == null){
                    res.put("Message", "Institution is empty");
                    throw new BadRequestException("Institution is empty");
                }
                Integer institutionId = editInstitutionRequest.getId();
                String institutionName = editInstitutionRequest.getName();
                
                Institutions existingInstitution = institutionDatabaseBean.getInstitution_ById(institutionId);
                if(existingInstitution == null){
                    res.put("Message", "The institution does not exist.");
                    throw new BadRequestException("The institution does not exist.");
                }
                Institutions existingInstitution2 = institutionDatabaseBean.getInstitution_ByName(institutionName);
                if(existingInstitution2 != null){
                    res.put("Message", "Institution already exists.");
                    throw new BadRequestException("Institution already exists.");
                }
                Institutions institution = new Institutions();
                institution.setId(institutionId);
                institution.setInstitutionName(institutionName);
                
            if (!transactionProvider.updateEntity(institution)) {
                res.put("Message", "Institution has not been updated");
                throw new PersistenceException("Institution has not been updated");
            }
            
            res.put("Message", "Institution " + institution.getInstitutionName() + " has been updated");
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
    
    
    public Response getAllInstitutions(){
        try{
            List<Institutions> institutionList = institutionDatabaseBean.getAllInstitutions();
            HashMap<String, Object> res = new HashMap();
            if(institutionList.isEmpty()){
                res.put("message", "No Institutions Exist");
            }
            else {
                List institutions = new ArrayList();
                for(Institutions institution : institutionList){
                    HashMap<String, Object> institutionHashMap = new HashMap();
                    institutionHashMap.put("name", institution.getInstitutionName());
                    institutionHashMap.put("id", institution.getId().toString());
                    institutions.add(institutionHashMap);
                }
                res.put("Institutions", institutions);
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
