/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.AgendaEntity;
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
public class AgendaPersistence {
    private static final Logger LOGGER = Logger.getLogger(AgendaPersistence.class.getName());
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;
    
     /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param agendaEntity la agenda que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public AgendaEntity create(AgendaEntity agendaEntity) 
    {
        LOGGER.log(Level.INFO, "Creando una agenda nueva");
        //Persiste el elemento
        em.persist(agendaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una agenda nueva");
        return agendaEntity;
    }
    
    /**
     * Devuelve todas las agendas de la base de datos.
     * @return una lista con todas las agendas que encuentre en la base de
     */
    public List<AgendaEntity> findAll() 
    {
        LOGGER.log(Level.INFO, "Consultando todas las agendas");
        // Se crea un query para buscar todas las agendas en la base de datos.
        TypedQuery query = em.createQuery("select u from AgendaEntity u", AgendaEntity.class);
        return query.getResultList();
    }
    
    /**
     * Busca agenda por su id
     *
     * @param agendaId: id de la agenda.
     * @return una agenda.
     */
    public AgendaEntity find(Long agendaId) 
    {
        LOGGER.log(Level.INFO, "Consultando agenda con id={0}", agendaId);
     
        return em.find(AgendaEntity.class, agendaId);
    }

    /**
     * Busca agenda por su id
     *
     * @param proveedorId: el proveedor de la agenda.
     * @return una agenda.
     */
    public AgendaEntity findByProveedor(long proveedorId) 
    {
        LOGGER.log(Level.INFO, "Consultando agenda por proveedor ", proveedorId);
        // Se crea un query para buscar editoriales con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From AgendaEntity e where e.proveedor.id = :proveedorId", AgendaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("proveedorId", proveedorId);
        // Se invoca el query se obtiene la lista resultado
        List<AgendaEntity> sameName = query.getResultList();
        AgendaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar agenda por proveedor ", proveedorId);
        return result;
    }
    
    /**
     * Actualiza una agenda.
     *
     * @param agendaEntity: la nueva agenda.
     * @return una agenda con los cambios aplicados.
     */
    public AgendaEntity update(AgendaEntity agendaEntity) 
    {
        LOGGER.log(Level.INFO, "Actualizando agenda con id = {0}", agendaEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar la agenda con id = {0}", agendaEntity.getId());
        return em.merge(agendaEntity);
    }
	
    /**
     *
     * Borra una agenda de la base de datos recibiendo como argumento el id
     * de la agenda
     *
     * @param agendaId: id correspondiente a la agenda a borrar.
     */
    public void delete(Long agendaId) {
        LOGGER.log(Level.INFO, "Borrando agenda con id = {0}", agendaId);
        // busca la agenda
        AgendaEntity entity = em.find(AgendaEntity.class, agendaId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la agenda con id = {0}", agendaId);
    }
	
}
