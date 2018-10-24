/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Andres
 */
@Stateless
public class EventoPersistence {

    /**
     * Log para el registro de eventos
     */
    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());

    /**
     * Manejador de entidades
     */
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param pEventoEntity
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EventoEntity create(EventoEntity pEventoEntity) {
        LOGGER.log(Level.INFO, "Creando un nuevo evento");

        em.persist(pEventoEntity);

        LOGGER.log(Level.INFO, "Saliendo de crear un evento");

        return pEventoEntity;

    }

    /**
     * Devuelve todos los eventos de la base de datos.
     *
     * @return una lista con todos los eventos que encuentre en la base de datos
     */
    public List<EventoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los eventos");

        TypedQuery query = em.createQuery("select u from EventoEntity u", EventoEntity.class);

        return query.getResultList();

    }

    /**
     * Busca si hay algun evento con el id que se envía de argumento
     *
     * @param pEventoId: id correspondiente al evento buscado
     * @return El evento con el Id dado
     */
    public EventoEntity find(Long pEventoId) {

        LOGGER.log(Level.INFO, "Buscando el evento solicitado");

        return em.find(EventoEntity.class, pEventoId);

    }

    /**
     * Borra de la base de datos el evento con el Id enviado por parametro
     *
     * @param pIdEvento: id del evento que se busca eliminar
     */
    public void delete(Long pIdEvento) {
        LOGGER.log(Level.INFO, "Borrando evento solicitado");

        EventoEntity entity = find(pIdEvento);

        em.remove(entity);

        LOGGER.log(Level.INFO, "Saliendo de borrar el evento solicitado");

    }

    /**
     * Acualiza un evento
     *
     * @param eventoEntity: objeto con el cual se modificará el existente
     * @return el evento actualizado
     */
    public EventoEntity update(EventoEntity eventoEntity) {

        LOGGER.log(Level.INFO, "Actualizando el evento solicitado");

        LOGGER.log(Level.INFO, "Saliendo de actualizar el evento solicitado");
        return em.merge(eventoEntity);
    }

    /**
     * Busca un evento por su nombre
     *
     * @param nombre: nombre del evento que se quiere buscar
     * @return objeto del evento encontrado
     */
    public EventoEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando evento por nombre = {0}", nombre);
        TypedQuery query = em.createQuery("Select e From EventoEntity e where e.nombre = :nombre", EventoEntity.class);

        query = query.setParameter("nombre", nombre);

        List<EventoEntity> sameName = query.getResultList();
        EventoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar evento por nombre = {0} ", nombre);
        return result;

    }

}
