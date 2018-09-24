/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class FechaPersistence {
    private static final Logger LOGGER = Logger.getLogger(AgendaPersistence.class.getName());
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param fechaEntity la fecha que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FechaEntity create(FechaEntity fechaEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una fecha nueva");
        //Persiste el elemento
        em.persist(fechaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una fecha nueva");
        return fechaEntity;
    }
    
    /**
     * Devuelve todas las agendas de la base de datos.
     * @return una lista con todas las agendas que encuentre en la base de
     */
    public List<FechaEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las  fechas");
        // Se crea un query para buscar todas las fechas en la base de datos.
        TypedQuery query = em.createQuery("select u from FechaEntity u", FechaEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca fecha por su id
     *
     * @param fechaId: id de la agenda.
     * @return una agenda.
     */
    public FechaEntity find(Long fechaId) 
    {
        LOGGER.log(Level.INFO, "Consultando fecha con id={0}", fechaId);
     
        return em.find(FechaEntity.class, fechaId);
    }

    /**
     * Actualiza una agenda.
     *
     * @param fechaEntity: la nueva agenda.
     * @return una fecha con los cambios aplicados.
     */
    public FechaEntity update(FechaEntity fechaEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando fecha con id = {0}", fechaEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar la fecha con id = {0}", fechaEntity.getId());
        return em.merge(fechaEntity);
    }
	
    /**
     *
     * Borra una fecha de la base de datos recibiendo como argumento el id
     * de la fecha
     *
     * @param fechaId: id correspondiente a la fecha a borrar.
     */
    public void delete(Long fechaId) {
        LOGGER.log(Level.INFO, "Borrando fecha con id = {0}", fechaId);
        // busca la agenda
        FechaEntity entity = em.find(FechaEntity.class, fechaId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la fecha con id = {0}", fechaId);
    }
    
    
    /**
     * Busca si hay alguna fecha con el dia, agenda y fecha que se envía de argumento
     *
     * @param dia: el dia de la fecha que se está buscando
     * @param idAgenda: idDe la agenda sobre la cual se busca 
     * @return null si no existe ninguna fecha con el dia del argumento.
     * Si existe alguna devuelve la primera.
     */
    public FechaEntity findByDiaAgendaAndJornada(Date dia,long idAgenda, String jornada) {
        LOGGER.log(Level.INFO, "Consultando fecha por dia {0}", dia);
        // Se crea un query para buscar fechas con el dia que recibe el método como argumento. 
        TypedQuery query = em.createQuery("Select e From FechaEntity e where e.dia = :dia and e.agenda.id = :idAgenda and e.jornada=:jornada", FechaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("dia", dia);
        query = query.setParameter("idAgenda", idAgenda);
        query = query.setParameter("jornada", jornada);
        
        // Se invoca el query se obtiene la lista resultado
        List<FechaEntity> sameName = query.getResultList();
        FechaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar fecha por dia {0}", dia);
        return result;
    }
}
