/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.EventoDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
@Path ("evento")
@Produces ("application/json")
@Consumes ("application/json")
@RequestScoped
/**
 *
 * @author estudiante
 */
public class EventoResource {
    
    /*
    Método vacío para pruebas en postman
    */
    @GET
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDTO consultarEvento(@PathParam("evento")String evento ){
        return new EventoDTO();
    }
      @POST
    public EventoDTO crearEvento( EventoDTO pEvento)
    {
        return pEvento ;
    }
    
      @DELETE
      @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public void borrarEvento(@PathParam("evento")String evento)
    {
        
    }
    
    @PUT
     @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDTO actualizarEvento(@PathParam("evento")String evento ,EventoDTO pEvento)
    {
        return pEvento ;
    }
    
    
    
}
