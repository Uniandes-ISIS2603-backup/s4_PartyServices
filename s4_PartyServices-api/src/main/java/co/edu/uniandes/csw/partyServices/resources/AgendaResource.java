/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.AgendaDTO;
import co.edu.uniandes.csw.partyServices.ejb.AgendaLogic;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author n.hernandezs
 */
@Path("agenda")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AgendaResource {
    
    @Inject
    private AgendaLogic agendaLogic;
    
    @GET 
    @Path("{proveedor: [a-zA-Z][a-zA-Z]*}")
    public AgendaDTO obtenerAgenda(@PathParam("proveedor") String proveedor)
    {
        return new AgendaDTO();
    }
    
    @POST 
    public AgendaDTO agregarAgenda(AgendaDTO agenda)
    {
        return agenda;
    }
    
    @PUT 
    @Path("{proveedor: [a-zA-Z][a-zA-Z]*}")
    public AgendaDTO actualizarAgenda(@PathParam("proveedor") String proveedor,AgendaDTO agenda)
    {
        return agenda;
    }
    @DELETE 
    @Path("{proveedor: [a-zA-Z][a-zA-Z]*}")
    public void eliminarAgenda(@PathParam("proveedor") String proveedor)
    {
        
    }
}
