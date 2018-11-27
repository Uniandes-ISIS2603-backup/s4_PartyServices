/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.ejb;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.util.ConstantesEvento;
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres
 */
@Stateless
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    /**
     * Persistencia de evento
     */
    @Inject
    private EventoPersistence persistence;
    
    @Inject
    private ClientePersistence clientePersistence ;

    /**
     * Metodo que busca un evento por su nombre en la base de datos
     * @param pNombre nombre del evento 
     * @return evento con el nombre correspondiente
     */
    public EventoEntity getEvento(String pNombre) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento por nombre");
        EventoEntity eventoEntity = persistence.findByName(pNombre);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "El evento con el nombre dado no existe ");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar evento por nombre");
        return eventoEntity;

    }

    /**
     * Crea un evento
     *
     * @param eventoEntity: evento a crear
     * @return entity del evento creado
     * @throws BusinessLogicException si las reglas de negocio no se cumplen
     */
    public EventoEntity createEvento(EventoEntity eventoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del evento ");

        if (eventoEntity.getCliente() == null) {
            throw new BusinessLogicException("El evento debe tener un cliente");
        }

        if (!(validateNombre(eventoEntity.getNombre()))) {
            throw new BusinessLogicException("El nombre del evento debe ser diferente de null y no puede ser vacio");
        }

        if (persistence.findByName(eventoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un evento con ese nombre");
        }

        if (eventoEntity.getNombre().length() < 3 || eventoEntity.getNombre().length() > 25) {
            throw new BusinessLogicException("El nombre del evento debe de tener entre 3 y 25 caracteres");
        }

        if (validateNombreCaracteres(eventoEntity.getNombre())) {
            throw new BusinessLogicException("El nombre no puede contener caracteres especiales");
        }

        if (!eventoEntity.getEstado().equals(ConstantesEvento.EN_PLANEACION)) {
            throw new BusinessLogicException("Para crear un evento su estado inicial debe ser en planeacion");
        }

//        if (eventoEntity.getFechas() == null) {
//            throw new BusinessLogicException("El evento debe tener una fecha");
//        }

        if (eventoEntity.getProductos() == null) {
            eventoEntity.setProductos(new ArrayList<>());
        }

        if (!validateLatitud(eventoEntity.getLatitud())) {
            throw new BusinessLogicException("La latitud del evento no se encuentra en colombia");
        }

        if (!validateLongitud(eventoEntity.getLongitud())) {
            throw new BusinessLogicException("La longitud del evento no se encuentra en colombia");
        }
 
        ClienteEntity cliente = clientePersistence.find(eventoEntity.getCliente().getId());
        
        System.out.println(cliente.getNombreUsuario());
        
        if(cliente == null)
        {
            throw new BusinessLogicException("El evento debe tener un cliente asociado");
        }
        List<EventoEntity> lista = cliente.getEventos() ;
       
        if(lista == null)
        {
            lista = new ArrayList<>() ;
        }
 
        lista.add(eventoEntity) ;
        
        cliente.setEventos(lista);
        
       
        cliente = clientePersistence.update(cliente) ;
        
        
        eventoEntity.setCliente(cliente);     
        

        LOGGER.log(Level.INFO, "Termino proceso de creación del evento");

        return persistence.create(eventoEntity);

    }

    /**
     * Valida que el nombre no sea nulo o vacio
     *
     * @param pNombre nombre a validar
     * @return true si es valido, false si no
     */
    public boolean validateNombre(String pNombre) {
        return !(pNombre == null || pNombre.isEmpty());
    }

    /**
     * Elimina el evento con el nombre dado por parametro
     *
     * @param pNombre nombre del evento el cual se busca eliminar
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    public void deleteEvento(String pNombre) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminación del evento");

        EventoEntity buscado = persistence.findByName(pNombre);

        if (buscado == null) {
            throw new BusinessLogicException("No existen eventos con el nombre solicitado");
        }
        persistence.delete(buscado.getId());

        LOGGER.log(Level.INFO, "Termina proceso de eliminación del evento");

    }

    /**
     * Actualiza un evento
     *
     * @param pNombre nombre del evento que se quiere modificar
     * @param pEventoEntity Objeto del nuevo evento
     * @return el evento actualizado
     * @throws BusinessLogicException si no se cumplan las reglas de negocio
     */
    public EventoEntity updateEvento(String pNombre, EventoEntity pEventoEntity) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "Comienza proceso de actualizacion del evento");

        if (!(validateNombre(pNombre))) {
            throw new BusinessLogicException("El nombre del evento es inválido");
        }

        EventoEntity newEntity = persistence.update(pEventoEntity);

        LOGGER.log(Level.INFO, "termina proceso de actualizacion del evento");
        return newEntity;
    }

    /**
     * metodo para validar que la longitud se encuentre dentro de Colombia
     *
     * @param pLongitud longitud a validar
     * @return true si esta dentro de Colombia, false de lo contrario
     */
    private boolean validateLongitud(Double pLongitud) {
        return (pLongitud != null && pLongitud >= -79.374594 && pLongitud <= -66.853233);
    }

    /**
     * metodo para validar que la latitud se encuentre dentro de Colombia
     *
     * @param pLatitud latitud a validar
     * @return true si esta dentro de Colombia, false de lo contrario
     */
    private boolean validateLatitud(Double pLatitud) {
        return (pLatitud != null && pLatitud >= -4.223596 && pLatitud <= 12.514801);
    }

    /**
     * Metodo que valida que el nombre de un evento no contenga caracteres
     * especiales
     *
     * @param nombre nombre a validar
     * @return true si cumple con el requisito, false de lo contrario
     */
    private boolean validateNombreCaracteres(String nombre) {
        Pattern pat = Pattern.compile("[a-zA-Z]");
        Matcher mat = pat.matcher(nombre);
        return (mat.matches());
    }

    /**
     * Metodo que retorna todos los eventos
     *
     * @return una lista con todos los eventos de la base de datos
     */
    public List<EventoEntity> findAll() {
        return persistence.findAll();
    }

    /**
     * Encuentra un evento en la base de datos con un nombre dado
     *
     * @param pNombre nombre del evento que se quiere buscar
     * @return el evento de la base de datos
     */
    public EventoEntity findByNombre(String pNombre) {
        return persistence.findByName(pNombre);
    }
    
    /**
     * Obtiene los datos de una instancia de Evento a partir de su ID.
     *
     * @param eventosId Identificador de la instancia a consultar
     * @return Instancia de EventoEntity con los datos del Evento consultado.
     * @author Tomas Vargas 
     */
    public EventoEntity getEvento(Long eventosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el evento con id = {0}", eventosId);
        EventoEntity eventoEntity = persistence.find(eventosId);
        if (eventoEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", eventosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el evento con id = {0}", eventosId);
        return eventoEntity;
    }

}
