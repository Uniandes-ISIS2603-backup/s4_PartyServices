/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Respresenta la relacion logica entre fecha y evento
 * @author Nicolas Hernandez
 */
@Stateless
public class FechaEventoLogic {
    
    /**
     * Persistencia de fecha
     */
    @Inject
    private FechaPersistence fechaPersistence;
    
    /**
     * Persistencia de evento
     */
    @Inject
    private EventoPersistence eventoPersistence;
    
    /**
     * Anade un evento a una fecha
     * @param fechaId fecha a la que se anadira el evento
     * @param eventoId evendo a anadir a la fecha
     * @return el evento anadido
     */
    public EventoEntity anadirEvento(long fechaId, long eventoId){
        FechaEntity fechaEntity = fechaPersistence.find(fechaId);
        EventoEntity eventoEntity = eventoPersistence.find(eventoId);
        eventoEntity.getFechas().add(fechaEntity);
        return eventoPersistence.find(eventoId);
    }
    
    /**
     * Remplaza los evento s de una fecha
     * @param idFecha la fecha a remplazar sus eventos
     * @param eventos los eventos a remplazar
     * @return los nuevos eventos de la fecha
     */
    public Collection<EventoEntity> remplazarEventos(long idFecha, Collection<EventoEntity> eventos)
    {
        FechaEntity fecha= fechaPersistence.find(idFecha);
        fecha.setEventos(eventos);
        List<EventoEntity> eventosEntities =eventoPersistence.findAll();
        for (EventoEntity eventosEntity : eventosEntities) {
            if(eventos.contains(eventosEntity)){
                if(!eventosEntity.getFechas().contains(fecha))
                    eventosEntity.getFechas().add(fecha);
                else 
                    eventosEntity.getFechas().remove(fecha);
            }
        }
        return fechaPersistence.find(idFecha).getEventos();
    }
    
    /**
     * Obtiene los eventos de una fecha
     * @param idFecha la fecha a obtener sus eventos
     * @return los eventos de la fecha
     */
    public Collection<EventoEntity> obtenerEventos(long idFecha)
    {
        return fechaPersistence.find(idFecha).getEventos();
    }

    /**
     * Elimina un evento de una fecha
     * @param fechaId fecha a eliminar su evento
     * @param eventoId evento a eliminar
     */
    public void eliminarEvento(long fechaId, long eventoId){
        FechaEntity fechaEntity = fechaPersistence.find(fechaId);
        EventoEntity eventoEntity = eventoPersistence.find(eventoId);
        eventoEntity.getFechas().remove(fechaEntity);
    }
    
}
