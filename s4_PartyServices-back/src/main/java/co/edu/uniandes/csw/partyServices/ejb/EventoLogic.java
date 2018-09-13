/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    EventoPersistence persistence;

    public EventoEntity getEvento(String pNombre) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento por nombre");
        EventoEntity eventoEntity = persistence.findByName(pNombre);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el nombre dado no existe ");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar evento por nombre");
        return eventoEntity;

    }

    public EventoEntity createEvento(EventoEntity eventoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del evento ");

        if (eventoEntity.getCliente() == null) 
        {
            throw new BusinessLogicException("El evento debe tener un cliente");
        }
        
        if (!(validateNombre(eventoEntity.getNombre()))) 
        {
            throw new BusinessLogicException("El nombre del evento debe ser diferente de null y no puede ser vacio");
        }
        
        if (persistence.findByName(eventoEntity.getNombre()) != null) 
        {
            throw new BusinessLogicException("Ya existe un evento con ese nombre");
        }
        
        if (eventoEntity.getNombre().length() < 3 || eventoEntity.getNombre().length() > 25) 
        {
            throw new BusinessLogicException("El nombre del evento debe de tener entre 3 y 25 caracteres");
        }
        
        if (!eventoEntity.getNombre().matches("[a-zA-Z0-9]")) 
        {
            throw new BusinessLogicException("El nombre no puede contener caracteres especiales");
        }

        if (eventoEntity.getEstado().equals(EventoEntity.Estado.EN_PLANEACION) == false) 
        {
            throw new BusinessLogicException("Para crear un evento su estado inicial debe ser en planeacion");
        }

        if (eventoEntity.getFecha() == null) 
        {
            throw new BusinessLogicException("El evento debe tener una fecha");
        }

        if (eventoEntity.getProductos() == null) 
        {
            throw new BusinessLogicException("El evento no puede tener productos nulos");
        }

        if (validateLatitud(eventoEntity.getLatitud())) 
        {
            throw new BusinessLogicException("La latitud del evento no se encuentra en colombia");
        }
        
        if (validateLongitud(eventoEntity.getLongitud())) 
        {
            throw new BusinessLogicException("La longitud del evento no se encuentra en colombia");
        }

        persistence.create(eventoEntity);

        LOGGER.log(Level.INFO, "Termino proceso de creación del evento");

        return eventoEntity;

    }

    public boolean validateNombre(String pNombre) {
        return !(pNombre == null || pNombre.isEmpty());
    }

    public void deleteEvento(String pNombre) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación del evento");

        EventoEntity buscado = persistence.findByName(pNombre);

        if (buscado == null) {
            throw new BusinessLogicException("No existen eventos con el nombre solicitado");
        } 
        persistence.delete(buscado.getId());

        LOGGER.log(Level.INFO, "Termina proceso de eliminación del evento");

    }

    public EventoEntity updateProducto(String pNombre, EventoEntity pEventoEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Comienza proceso de actualizacion del evento");

        if (!(validateNombre(pNombre))) 
        {
            throw new BusinessLogicException("El nombre del evento es inválido");
        }
        
        EventoEntity newEntity = persistence.update(pEventoEntity);

        LOGGER.log(Level.INFO, "termina proceso de actualizacion del evento");
        return newEntity;
    }

    private boolean validateLongitud(Double pLongitud) {
        return (pLongitud != null && pLongitud >= -79.374594 && pLongitud <= -66.853233);
    }

    private boolean validateLatitud(Double pLatitud) {
        return (pLatitud != null && pLatitud >= -4.223596 && pLatitud <= 12.514801);
    }

}
