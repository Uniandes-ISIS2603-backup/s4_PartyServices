/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.NotificacionDTO;
import co.edu.uniandes.csw.partyServices.dtos.ProveedorDTO;
import javax.enterprise.context.RequestScoped;
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
 * @author estudiante
 */
@Path("notificacion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class NotificacionResource {
   
     @GET
    public NotificacionDTO getNotificacion(){
        return new NotificacionDTO();
    }
        @POST
    public NotificacionDTO crearNotificacion(NotificacionDTO pNotif)
    {
        return pNotif ;
    }
    
      @DELETE
      @Path("{notificacion: [a-zA-Z][a-zA-Z]*}")
    public void borrarNotificacion(@PathParam("notificacion")String jotificacion)
    {
        
    }
    
    @PUT
    @Path("{notificacion: [a-zA-Z][a-zA-Z]*}")
    public NotificacionDTO actualizarNotificacion(@PathParam("notificacion")String notificacion, NotificacionDTO pNotif)
    {
        return pNotif ;
    }
      
}