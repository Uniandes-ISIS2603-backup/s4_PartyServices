/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.AgendaDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author n.hernandezs
 */
@Path("agenda")
@Produces("aplication/json")
@Consumes("aplication/json")
@RequestScoped
public class AgendaResource {
    
    @GET 
    public AgendaDTO obtenerAgenda()
    {
        return new AgendaDTO();
    }
}
