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
 *
 * @author Nicolas Hernandez
 */
@Stateless
public class FechaEventoLogic {
    
    @Inject
    private FechaPersistence fechaPersistence;
    
    @Inject
    private EventoPersistence eventoPersistence;
    
    
    public Collection<EventoEntity> remplazarEventos(long idFecha, Collection<EventoEntity> eventos)
    {
        FechaEntity fecha= fechaPersistence.find(idFecha);
        fecha.setEventos(eventos);
        List<EventoEntity> eventosEntities =eventoPersistence.findAll();
        for (EventoEntity eventosEntity : eventosEntities) {
            if(eventos.contains(eventosEntity)){
                if(!eventosEntity.getFechas().contains(fecha))
                    eventosEntity.getFechas().add(fecha);
            }
        }
        return fechaPersistence.find(idFecha).getEventos();
    }
    
    public Collection<EventoEntity> obtenerEventos(long idFecha)
    {
        return fechaPersistence.find(idFecha).getEventos();
    }

    
}
