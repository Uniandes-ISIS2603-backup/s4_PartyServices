/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.NotificacionDTO;
import co.edu.uniandes.csw.partyServices.ejb.NotificacionLogic;
import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author estudiante
 */
@Path("notificacion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class NotificacionResource {
   private static final Logger LOGGER = Logger.getLogger(NotificacionResource.class.getName());

    @Inject
    private NotificacionLogic notificacionLogic;
     @GET
    public NotificacionDTO getNotificacion(@PathParam("notificacionesId") Long notificacionesId){
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: input: {0}", notificacionesId);
        NotificacionEntity notifEntity = notificacionLogic.getNotificacion(notificacionesId);
        if (notifEntity == null) {
            throw new WebApplicationException("El recurso /notificacion/" + notificacionesId + " no existe.", 404);
        }
        NotificacionDTO nDTO = new NotificacionDTO(notifEntity);
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: output: {0}", nDTO.toString());
        return nDTO;
    }
    
    
        @GET
    public List<NotificacionDTO> getNotificaciones() {
        LOGGER.info("NotificacionResource getNotificacion: input: void");
        List<NotificacionDTO> listaNotifs = listEntity2DTO(notificacionLogic.getNotificacion());
        LOGGER.log(Level.INFO, "NotificacionResource getNotificacion: output: {0}", listaNotifs.toString());
        return listaNotifs;
    }
    
        @POST
    public NotificacionDTO crearNotificacion(NotificacionDTO pNotif) throws BusinessLogicException
    { LOGGER.log(Level.INFO, "NotificacionResource createNotificacion: input: {0}", pNotif.toString());
        NotificacionDTO notificacionDTO = new NotificacionDTO(notificacionLogic.createNotificacion(pNotif.toEntity()));
        LOGGER.log(Level.INFO, "NotificacionResource createNotificacion: output: {0}", notificacionDTO.toString());
        return notificacionDTO;
    }
    
      @DELETE
      @Path("{notificacion: [a-zA-Z][a-zA-Z]*}")
    public void borrarNotificacion(@PathParam("notificacion")long notificacionID) throws BusinessLogicException
    {
         LOGGER.log(Level.INFO, "NotificacionResource deleteNotificacion: input: {0}", notificacionID);
        if (notificacionLogic.getNotificacion(notificacionID) == null) {
            throw new WebApplicationException("El recurso /notificacion/" + notificacionID + " no existe.", 404);
        }
        notificacionLogic.deleteNotificacion(notificacionID);
        LOGGER.info("NotificacionResource deleteNotificacion: output: void");
        
    }
    
    @PUT
    @Path("{notificacion: [a-zA-Z][a-zA-Z]*}")
    public NotificacionDTO actualizarNotificacion(@PathParam("notificacion")Long notifID, NotificacionDTO pNotif) throws BusinessLogicException
    {
       LOGGER.log(Level.INFO, "NotificacionResource updateNotificacion: input: authorsId: {0} , author: {1}", new Object[]{notifID, pNotif.toString()});
        pNotif.setID(notifID);
        if (notificacionLogic.getNotificacion(notifID) == null) {
            throw new WebApplicationException("El recurso /notificacion/" + notifID + " no existe.", 404);
        }
        NotificacionDTO notDTO = new NotificacionDTO(notificacionLogic.updateNotificacion(notifID, pNotif.toEntity()));
        LOGGER.log(Level.INFO, "NotificacionResource updateNotificacion: output: {0}", notDTO.toString());
        return notDTO;
    }
    
    
         private List<NotificacionDTO> listEntity2DTO(List<NotificacionEntity> entityList) {
        List<NotificacionDTO> list = new ArrayList<>();
        for (NotificacionEntity entity : entityList) {
            list.add(new NotificacionDTO(entity));
        }
        return list;
    } 
}