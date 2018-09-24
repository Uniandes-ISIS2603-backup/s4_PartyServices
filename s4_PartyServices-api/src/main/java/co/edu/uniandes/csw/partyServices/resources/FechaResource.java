/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.FechaDTO;
import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.Date;
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
    @Path("{id: \\d+}")
    public FechaDTO getFechaId(@PathParam("dia") long dia) throws BusinessLogicException
    {
        return new FechaDTO(fechaLogic.getFechaID(dia));
    }
    @GET
    @Path("{agenda: \\d+}/{fecha: [a-zA-Z][a-zA-Z]*}/{jornada: [a-zA-Z][a-zA-Z]*}")
   
    public FechaDTO getFechaAgendaDiaJornada(
            @PathParam("agenda") long agenda,
            @PathParam("fecha")String fecha,
            @PathParam("jornada")String jornada) throws BusinessLogicException
    {
        Date dia =new Date(fecha);
        return new FechaDTO(fechaLogic.getFechaPorDiaAgendaJornada(dia,agenda,jornada));
    }
    
    @POST
    @Path("{agenda: \\d+}")
    public FechaDTO anadirFecha( @PathParam("agenda") long agenda,FechaDTO fecha) throws BusinessLogicException
    {
        return new FechaDTO(fechaLogic.createFecha(agenda, fecha.toEntity()));
    }
    @PUT
    public FechaDTO actualizarFecha(FechaDTO fecha) throws BusinessLogicException
    {
        return new FechaDTO(fechaLogic.updateFecha(fecha.toEntity()));
    }
    @DELETE
    @Path("{id: \\d+}")
    public void eliminarFecha(@PathParam("id") long id) throws BusinessLogicException
    {
        fechaLogic.deleteFecha(id);
    }
   
    
}
