/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.AgendaDTO;
import co.edu.uniandes.csw.partyServices.dtos.AgendaDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.AgendaLogic;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *Recurso de agenda
 * @author n.hernandezs
 */
@Path("agenda")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AgendaResource {
    
    private static final Logger LOGGER = Logger.getLogger(AgendaResource.class.getName());
    
    /**
     * Logica de agenda
     */
    @Inject
    private AgendaLogic agendaLogic;
    
    /**
     * Obtener agenda. Obtiene la agenda por su respectivo id
     * @param id de la agenda
     * @return la agenda a buscar
     * @throws BusinessLogicException si se incumple alguna regla de negocio
     */
    @GET 
    @Path("{id: \\d+}")
    public AgendaDTO obtenerAgenda(@PathParam("id") long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AgendaResource obtenerAgenda: input: {0}", id);
        return new AgendaDetailDTO(agendaLogic.getAgenda(id));
    }
    
    /**
     * Obtener agenda por proveedor. Obtiene la agenda por el id del proveedor
     * @param id del proveedor
     * @return la agenda que se esta buscando
     * @throws BusinessLogicException si se incumple alguna regla de negocio
     */
    @GET 
    @Path("proveedor/{id: \\d+}")
    public AgendaDTO obtenerAgendaPorProveedor(@PathParam("id") long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AgendaResource obtenerAgenda: input: {0}", id);
        return new AgendaDetailDTO(agendaLogic.getAgendaByProveedor(id));
    }
    
    /**
     * Agregar agenda. Agrega una agenda a la base de datos
     * @param proveedor el proveedor duenio de la agenda
     * @param agenda la agenda que se va a crear
     * @return la agenda creada
     * @throws BusinessLogicException si la agenda no cumple con las reglas de negocio 
     */
    @POST 
    @Path("{proveedor: \\d+}")
    public AgendaDTO agregarAgenda(@PathParam("proveedor") long proveedor,AgendaDTO agenda) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AgendaResource agregarAgenda:", agenda.getId());
        return new AgendaDetailDTO(agendaLogic.createAgenda(proveedor, agenda.toEntity()));
    }
    
    /**
     * Actualizar agenda. Actualiza la agenda
     * @param agenda la agenda a actualizar
     * @return la agenda actualizada
     * @throws BusinessLogicException si la agenda a actualizar incumple con alguna regla de negocio
     */
    @PUT 
    public AgendaDTO actualizarAgenda(AgendaDTO agenda) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AgendaResource actualizarAgenda:", agenda.getId());
        return new AgendaDTO(agendaLogic.updateAgenda(agenda.toEntity()));
    }
    /**
     * Eliminar agenda. Elimina una agenda por su id
     * @param id de la agenda a eliminar
     */
    @DELETE 
    @Path("{id: \\d+}")
    public void eliminarAgenda(@PathParam("id") long id)
    {
        LOGGER.log(Level.INFO, "AgendaResource eliminarAgenda:", id);
        
        agendaLogic.deleteAgenda(id);
    }
}
