/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.partyServices.dtos.FechaDTO;
import co.edu.uniandes.csw.partyServices.dtos.FechaDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.FechaEventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.ws.rs.WebApplicationException;

/**
 *Resource de fecha
 * @author estudiante
 */
@Path("fecha")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FechaResource {
    
    /**
     * Logica de la fecha
     */
    @Inject
    private FechaLogic fechaLogic;
    
    @Inject
    private FechaEventoLogic fechaEventoLogic;
    
    @Inject 
    private EventoLogic eventoLogic;
    
    
    /**
     * Obtener fecha por id. Obtiene la fecha por su id
     * @param id id de la fecha
     * @return la fecha a buscar
     * @throws BusinessLogicException si se incumple alguna regla de negocio 
     */
    @GET
    @Path("{id: \\d+}")
    public FechaDTO getFechaId(@PathParam("id") long id) throws BusinessLogicException
    {
        return new FechaDetailDTO(fechaLogic.getFechaID(id));
    }
    
    /**
     * Obtener fechas de una agenda. Obtiene las fechas deuna agenda
     * @param id id de la agenda
     * @return el arreglo de las fechas que pertenecen a la agenda
     * @throws BusinessLogicException si se incumplen reglas de negocio
     */
    @GET
    @Path("idAgenda/{agenda: \\d+}")
    public FechaDTO[] getFechasDeAgenda(@PathParam("agenda") long id) throws BusinessLogicException
    {
        Collection<FechaEntity> fechas =fechaLogic.getFechasDeAgenda(id);
        FechaDTO[] respuesta= new FechaDTO[fechas.size()];
        int i=0;
        for (FechaEntity fecha : fechas) {
            respuesta[i]=new FechaDTO(fecha);
            i++;
        }
        return respuesta;
    }
    
    /**
     * Obtener fecha por su agenda, fecha y jornada. Busca la fecha que cumpla con los criterios mencionados
     * @param agenda agenda de la fecha
     * @param fecha dia de la fecha
     * @param jornada jornada de la fecha
     * @return la fecha que se busca
     * @throws BusinessLogicException si se invalida alguna regla de negocio 
     */
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
    
    /**
     * Anadir una fecha. Crea una fecha en la base de datos
     * @param agenda agenda de la fecha
     * @param fecha fecha a agregar
     * @return la fecha agregada
     * @throws BusinessLogicException si se incumple alguna regla de negocio 
     */
    @POST
    @Path("{agenda: \\d+}")
    public FechaDetailDTO anadirFecha( @PathParam("agenda") long agenda,FechaDTO fecha) throws BusinessLogicException
    {
        FechaEntity fechaNueva = fechaLogic.createFecha(agenda, fecha.toEntity());
        return new FechaDetailDTO(fechaNueva);
    }
    
    /**
     * Actualizar una fecha. Actualiza la fecha
     * @param fecha fecha a actualizar
     * @return la fecha actualizada
     * @throws BusinessLogicException si la fecha a actualizar no cumple con las reglas de negocio 
     */
    @PUT
    public FechaDetailDTO actualizarFecha(FechaDTO fecha) throws BusinessLogicException
    {
        return new FechaDetailDTO(fechaLogic.updateFecha(fecha.toEntity()));
    }
    
    /**
     * Eliminar una fecha. Elimina la fecha de la base de datos
     * @param id id de la fecha a eliminar
     * @throws BusinessLogicException si se incumple alguna regla de negocio
     */
    @DELETE
    @Path("{id: \\d+}")
    public void eliminarFecha(@PathParam("id") long id) throws BusinessLogicException
    {
        fechaLogic.deleteFecha(id);
    }
   
    
//    public Class<FechaEventoResource> getFechaEventoResource(@PathParam("idFecha") long idFecha) throws BusinessLogicException{
//        if (fechaLogic.getFechaID(idFecha) == null) {
//            throw new WebApplicationException("El recurso /fecha/" + idFecha + " no existe.", 404);
//        }
//        return FechaEventoResource.class;
//    }
    
    
    /**
     * Anade un evento a una fecha
     * @param idFecha fecha de la agenda
     * @param idEvento evento a anadir
     * @return el evento anadido
     * @throws BusinessLogicException si no existe la fecha o el evento
     */
    @POST
    @Path("{idFecha: \\d+}/eventos/{idEvento: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("idFecha") long idFecha, @PathParam("idEvento") long idEvento) throws BusinessLogicException {
        if (fechaLogic.getFechaID(idFecha) == null) {
            throw new WebApplicationException("La fecha no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(fechaEventoLogic.anadirEvento(idFecha, idEvento));
        return detailDTO;
    }
    
    /**
     * Elimina el evento de una fecha
     * @param idFecha fecha a la cual se eliminara su evento
     * @param idEvento evento a eliminar
     * @throws BusinessLogicException Si la fecha no existe
     */
    @DELETE
    @Path("{idFecha: \\d+}/eventos/{idEvento: \\d+}")
    public void deleteEvento(@PathParam("idFecha") long idFecha, @PathParam("idEvento") long idEvento) throws BusinessLogicException {
        if (fechaLogic.getFechaID(idFecha) == null) {
            throw new WebApplicationException("La fecha no existe.", 404);
        }
        fechaEventoLogic.eliminarEvento(idFecha, idEvento);
    }
    
    /**
     * Actualiza los evento de una fecha
     * @param idFecha fecha a la cual se actualizaran sus eventos
     * @param eventos eventos de la fecha a actualizar
     * @return los eventos de la fecha
     */
    @PUT
    @Path("{idFecha: \\d+}/eventos")
    public Collection<EventoDetailDTO> actualizarEventosDeFecha(@PathParam("idFecha") long idFecha, Collection<EventoDetailDTO> eventos)
    {
        //Verifica que los eventos si existan
        for (EventoDetailDTO evento : eventos) {
            if(eventoLogic.getEvento(evento.getNombre())==null)
                throw new WebApplicationException("El recurso /evento/" + evento.getId() + " no existe.", 404);
           
        }
        return collectionEntityAEventoDTO(fechaEventoLogic.remplazarEventos(idFecha, collectionEventoDTOaEntity(eventos)));
    
    }
    
    /**
     * Obtiene los eventos de una fecha
     * @param idFecha fecha de la cual se van a obtener los eventos
     * @return los eventos de la fecha
     */
    @GET
    @Path("{idFecha: \\d+}/eventos")
    public Collection<EventoDetailDTO> obtenerEventosDeFecha(@PathParam("idFecha") long idFecha)
    {
       
        return collectionEntityAEventoDTO(fechaEventoLogic.obtenerEventos(idFecha));
    
    }
    
    /**
     * Convierte de DTO a entidad
     * @param eventos una coleccion de eventosDTO
     * @return una coleeciion de eventoEntities
     */
    public Collection<EventoEntity> collectionEventoDTOaEntity(Collection<EventoDetailDTO> eventos){
        Collection<EventoEntity> lista= new ArrayList<>();
        for (EventoDetailDTO evento : eventos) {
            lista.add(evento.toEntity());
        }
        return lista;
    }
    
    /**
     * Convierte de Entitiyes a DTOs
     * @param eventos una coleccion de eventoEntitites
     * @return una coleccion de eventosDTO
     */
    public Collection<EventoDetailDTO> collectionEntityAEventoDTO(Collection<EventoEntity> eventos){
        Collection<EventoDetailDTO> lista= new ArrayList<>();
        for (EventoEntity evento : eventos) {
            lista.add(new EventoDetailDTO(evento));
        }
        return lista;
    }
    
    
}
