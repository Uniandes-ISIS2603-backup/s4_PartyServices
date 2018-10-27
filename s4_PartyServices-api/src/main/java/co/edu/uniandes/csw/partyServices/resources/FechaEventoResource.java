/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.FechaEventoLogic;
import co.edu.uniandes.csw.partyServices.ejb.FechaLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Nicolas Hernandez
 */

@Produces("application/json")
@Consumes("application/json")
public class FechaEventoResource {
    private static final Logger LOGGER = Logger.getLogger(FechaEventoResource.class.getName());
    
//    @Inject
//    private FechaLogic fechaLogic;
    
//    @Inject
//    private EventoLogic eventoLogic;
    
//    @Inject
//    private FechaEventoLogic fechaEventoLogic;

    @PUT
    public EventoDetailDTO actualizarEventosDeFecha(@PathParam("idFecha") long idFecha, Collection<EventoDetailDTO> eventos)
    {
        //Verifica que los eventos si existan
//        for (EventoDetailDTO evento : eventos) {
//            if(eventoLogic.getEvento(evento.getNombre())==null)
//                throw new WebApplicationException("El recurso /evento/" + evento.getId() + " no existe.", 404);
//           
//        }
//        return collectionEntityAEventoDTO(fechaEventoLogic.remplazarEventos(idFecha, collectionEventoDTOaEntity(eventos)));
    
        EventoDetailDTO a= new EventoDetailDTO();
        a.setId(idFecha);
        return a;
    }
    
    public Collection<EventoEntity> collectionEventoDTOaEntity(Collection<EventoDetailDTO> eventos){
        Collection<EventoEntity> lista= new ArrayList<>();
        for (EventoDetailDTO evento : eventos) {
            lista.add(evento.toEntity());
        }
        return lista;
    }
    
    public Collection<EventoDetailDTO> collectionEntityAEventoDTO(Collection<EventoEntity> eventos){
        Collection<EventoDetailDTO> lista= new ArrayList<>();
        for (EventoEntity evento : eventos) {
            lista.add(new EventoDetailDTO(evento));
        }
        return lista;
    }
}
