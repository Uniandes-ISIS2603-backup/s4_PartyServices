/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.AgendaDTO;
import co.edu.uniandes.csw.partyServices.ejb.AgendaLogic;
import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
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
    @Path("{id: \\d+}")
    public AgendaDTO obtenerAgenda(@PathParam("id") long id)
    {
        return new AgendaDTO(agendaLogic.getAgenda(id));
    }
    
    @POST 
    @Path("{proveedor: \\d+}")
    public AgendaDTO agregarAgenda(@PathParam("proveedor") long proveedor,AgendaDTO agenda) throws BusinessLogicException
    {
        return new AgendaDTO(agendaLogic.createAgenda(proveedor, agenda.toEntity()));
    }
    
    @PUT 
    public AgendaDTO actualizarAgenda(AgendaDTO agenda) throws BusinessLogicException
    {
        return new AgendaDTO(agendaLogic.updateAgenda(agenda.toEntity()));
    }
    @DELETE 
    @Path("{id: \\d+}")
    public void eliminarAgenda(@PathParam("id") long id)
    {
        agendaLogic.deleteAgenda(id);
    }
}
