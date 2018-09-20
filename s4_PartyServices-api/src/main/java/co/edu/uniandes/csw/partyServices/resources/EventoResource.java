/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.EventoDTO;
import co.edu.uniandes.csw.partyServices.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
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
import javax.ws.rs.WebApplicationException;

@Path("evento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
/**
 *
 * @author estudiante
 */
public class EventoResource {

    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    @Inject
    private EventoLogic eventoLogic;

    
    
    
    @GET
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDetailDTO consultarEvento(@PathParam("evento") String evento) 
    {

        
        LOGGER.log(Level.INFO, "AuthorResource getAuthor: input: {0}", evento);
        EventoEntity evenEntity = eventoLogic.findByNombre(evento);
        
        if (evenEntity == null) {
            throw new WebApplicationException("El recurso /authors/" + evento + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(evenEntity);
        LOGGER.log(Level.INFO, "AuthorResource getAuthor: output: {0}", detailDTO.toString());
        return detailDTO;
      
     
    }

    @POST
    public EventoDTO crearEvento(EventoDTO pEvento) throws BusinessLogicException 
    {
         LOGGER.log(Level.INFO, "EventoResource createEvento: input: {0}", pEvento.toString());
        EventoDTO eventoDTO = new EventoDTO(eventoLogic.createEvento(pEvento.toEntity()));
        LOGGER.log(Level.INFO, "EventoResource createEvento: output: {0}", eventoDTO.toString());
        return eventoDTO;
    }

    @DELETE
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public void borrarEvento(@PathParam("evento") String evento) throws BusinessLogicException 
    {
        
         LOGGER.log(Level.INFO, "EventoResource deleteProducto: input: {0}", evento);
        EventoEntity entity = eventoLogic.findByNombre(evento);
        
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /eventos/" + evento + " no existe.", 404);
        }
     
        eventoLogic.deleteEvento(evento);
       
        LOGGER.info("EventoResource deleteProducto: output: void");
    }

    @PUT
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDTO actualizarEvento(@PathParam("evento") String evento, EventoDTO pEvento) throws BusinessLogicException 
    {
        
          LOGGER.log(Level.INFO, "EventoResource updateBook: input: id: {0} , book: {1}");
        pEvento.setNombre(evento);
        EventoEntity entity = eventoLogic.findByNombre(evento);
        if (entity== null) 
        {
            throw new WebApplicationException("El recurso /eventos/" + evento + " no existe.", 404);
        }
        
        EventoEntity updateado = eventoLogic.updateEvento(evento,pEvento.toEntity());
        
       EventoDTO detailDTO = new EventoDTO(updateado);
       
        LOGGER.log(Level.INFO, "EventoResource updateBook: output: {0}", detailDTO.toString());
        
        return detailDTO;
    }

}
