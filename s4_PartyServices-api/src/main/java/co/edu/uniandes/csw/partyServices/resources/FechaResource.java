/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.FechaDTO;
import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
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
 * @author estudiante
 */
@Path("fecha")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FechaResource {
    
    @Inject
    private FechaLogic fechaLogic;
    
    @GET
    @Path("{dia: \\d+}")
    public FechaDTO getFecha(@PathParam("dia") int dia)
    {
        return new FechaDTO();
    }
    @POST
    public FechaDTO anadirFecha(FechaDTO fecha)
    {
        return fecha;
    }
    @PUT
    @Path("{dia: \\d+}")
    public FechaDTO actualizarFecha(@PathParam("dia") int dia,FechaDTO fecha)
    {
        return fecha;
    }
    @DELETE
    @Path("{dia: \\d+}")
    public FechaDTO eliminarFecha(@PathParam("dia") int dia)
    {
        return new FechaDTO();
    }
   
    
}
