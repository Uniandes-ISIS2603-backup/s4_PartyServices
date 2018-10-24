/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.resources;

import co.edu.uniandes.csw.partyServices.dtos.EventoDTO;
import co.edu.uniandes.csw.partyServices.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.partyServices.ejb.EventoLogic;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Path("evento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
/**
 *
 * @author Andres
 */
public class EventoResource {

    /**
     * Log para el registro de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(EventoResource.class.getName());

    /**
     * Variable para acceder a la lógica de la aplicación. Es una inyección de
     * dependencias.
     */
    @Inject
    private EventoLogic eventoLogic;

    /**
     * Busca y devuelve todos los eventos que existen en la aplicacion.
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos() {
        List<EventoDetailDTO> listaEventos = new ArrayList<>();
        LOGGER.info("EventoResource getEventos: input: void");
        for (EventoEntity even : eventoLogic.findAll()) {
            listaEventos.add(new EventoDetailDTO(even));
        }
        LOGGER.log(Level.INFO, "ProductoResource getBooks: output: {0}", listaEventos);
        return listaEventos;
    }

    /**
     * Busca el evento con el nombre recibido en la URL y lo retorna
     *
     * @param evento Nombre del evento que se está buscando
     * @return El evento buscado
     */
    @GET
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDetailDTO consultarEvento(@PathParam("evento") String evento) {

        LOGGER.log(Level.INFO, "EventoResource getEvento: input: {0}", evento);
        EventoEntity evenEntity = eventoLogic.findByNombre(evento);

        if (evenEntity == null) {
            throw new WebApplicationException("El recurso /eventos/" + evento + " no existe.", 404);
        }
        EventoDetailDTO detailDTO = new EventoDetailDTO(evenEntity);
        LOGGER.log(Level.INFO, "EventoResource getEvento: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Crea un nuevo evento con la informacion que se recibe en el cuerpo de la
     * peticion y se regresa un objeto identico con un id auto-generado por la
     * base de datos
     *
     * @param pEvento el evento que se desea guardar
     * @return El evento guardado
     * @throws BusinessLogicException Si alguna de las reglas de negocio no se
     * cumple
     */
    @POST
    public EventoDTO crearEvento(EventoDTO pEvento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EventoResource createEvento: input: {0}", pEvento);
        EventoDTO eventoDTO = new EventoDTO(eventoLogic.createEvento(pEvento.toEntity()));
        LOGGER.log(Level.INFO, "EventoResource createEvento: output: {0}", eventoDTO);
        return eventoDTO;
    }

    /**
     * Elimina el evento con el nombre recibido en la URL
     *
     * @param evento nombre del evento que se quiere borrar
     * @throws BusinessLogicException si alguna de las reglas de negocio no se
     * cumple
     */
    @DELETE
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public void borrarEvento(@PathParam("evento") String evento) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "EventoResource deleteEvento: input: {0}", evento);
        EventoEntity entity = eventoLogic.findByNombre(evento);

        if (entity == null) {
            throw new WebApplicationException("El recurso /eventos/" + evento + " no existe.", 404);
        }

        eventoLogic.deleteEvento(evento);

        LOGGER.info("EventoResource deleteEvento: output: void");
    }

    /**
     * Actualiza el evento con el nombre recibido en la URL con los valores
     * recibidos en el cuerpo de la petición
     *
     * @param evento nombre del evento que se busca actualizar
     * @param pEvento Evento con la informacion que se desea actualizar
     * @return El objeto actualizado
     * @throws BusinessLogicException Si alguna de las reglas de nogocio no se
     * cumple
     */
    @PUT
    @Path("{evento: [a-zA-Z][a-zA-Z]*}")
    public EventoDTO actualizarEvento(@PathParam("evento") String evento, EventoDTO pEvento) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "EventoResource updateEvento");
        pEvento.setNombre(evento);
        EventoEntity entity = eventoLogic.findByNombre(evento);
        if (entity == null) {
            throw new WebApplicationException("El recurso /eventos/" + evento + " no existe.", 404);
        }

        EventoEntity updateado = eventoLogic.updateEvento(evento, pEvento.toEntity());

        EventoDTO detailDTO = new EventoDTO(updateado);

        LOGGER.log(Level.INFO, "EventoResource updateEvento: output: {0}", detailDTO);

        return detailDTO;
    }

}
