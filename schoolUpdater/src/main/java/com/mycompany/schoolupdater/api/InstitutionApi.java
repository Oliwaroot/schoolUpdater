/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.schoolupdater.api;

import com.mycompany.schoolupdater.ejb.InstitutionBean;
import com.mycompany.schoolupdater.requests.AddInstitutionRequest;
import com.mycompany.schoolupdater.requests.EditInstitutionRequest;
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
@Path("/institution")
@Produces({MediaType.APPLICATION_JSON})
public class InstitutionApi {
    @EJB
    InstitutionBean institutionBean;
    
    @GET
    public Response getAllInstitutions(){
        return institutionBean.getAllInstitutions();
    }
    
    @PUT
    public Response editInstitution(EditInstitutionRequest editInstitutionRequest) {
        return institutionBean.editInstitution(editInstitutionRequest);
    }
    
    @POST
    public Response addInstitution(AddInstitutionRequest addInstitutionRequest){
        return institutionBean.addInstitution(addInstitutionRequest);
    }
    
    @DELETE
    public Response deleteInstitution(@QueryParam("institution_id")Integer institutionId) {
        return institutionBean.deleteInstitution(institutionId);
    }
}
