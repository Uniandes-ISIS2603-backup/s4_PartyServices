/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.FechaDTO;
import co.edu.uniandes.csw.partyServices.dtos.FechaDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
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
    public FechaDTO getFechaId(@PathParam("id") long id) throws BusinessLogicException
    {
        return new FechaDetailDTO(fechaLogic.getFechaID(id));
    }
    @GET
    @Path("{agenda: \\d+}/{fecha}/{jornada: [a-zA-Z][a-zA-Z]*}")
   
    public FechaDTO getFechaAgendaDiaJornada(
            @PathParam("agenda") long agenda,
            @PathParam("fecha")String fecha,
            @PathParam("jornada")String jornada) throws BusinessLogicException
    {
        String[] splitFechaYHora=fecha.split("T");
        String[] splitFecha =splitFechaYHora[0].split("-");
        int anio=Integer.parseInt(splitFecha[0]);
        int mes=Integer.parseInt(splitFecha[1]);
        int dia= Integer.parseInt(splitFecha[2]);
       
        Date fechaDate=new Date(anio-1900, mes-1, dia);
        return new FechaDetailDTO(fechaLogic.getFechaPorDiaAgendaJornada(fechaDate,agenda,jornada));
    }
    
    @POST
    @Path("{agenda: \\d+}")
    public FechaDetailDTO anadirFecha( @PathParam("agenda") long agenda,FechaDTO fecha) throws BusinessLogicException
    {
        FechaEntity fechaNueva = fechaLogic.createFecha(agenda, fecha.toEntity());
        return new FechaDetailDTO(fechaNueva);
    }
    @PUT
    public FechaDetailDTO actualizarFecha(FechaDTO fecha) throws BusinessLogicException
    {
        return new FechaDetailDTO(fechaLogic.updateFecha(fecha.toEntity()));
    }
    @DELETE
    @Path("{id: \\d+}")
    public void eliminarFecha(@PathParam("id") long id) throws BusinessLogicException
    {
        fechaLogic.deleteFecha(id);
    }
   
    
}
