/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.TematicaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Tematica. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Tomas Vargas
 */

@Stateless
public class TematicaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(TematicaPersistence.class.getName());

    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param tematicaEntity objeto tematica que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TematicaEntity create(TematicaEntity tematicaEntity) {
        LOGGER.log(Level.INFO, "Creando una tematica nueva");
        em.persist(tematicaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una tematica nueva");
        return tematicaEntity;
    }
    
    /**
     * Devuelve todas las tematicas de la base de datos.
     *
     * @return una lista con todas las tematicas que encuentre en la base de
     * datos, "select u from TematicaEntity u" es como un "select * from
     * TematicaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<TematicaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las tematicas");
        
        TypedQuery query = em.createQuery("select u from TematicaEntity u", TematicaEntity.class);
        
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna tematica con el id que se envía de argumento
     *
     * @param tematicasId: id correspondiente a la tematica buscada.
     * @return una tematica.
     */
    public TematicaEntity find(Long tematicasId) {
        LOGGER.log(Level.INFO, "Consultando tematica con id={0}", tematicasId);
        
        return em.find(TematicaEntity.class, tematicasId);
    }
    
    /**
     * Actualiza una tematica.
     *
     * @param tematicaEntity: la tematica que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una tematica con los cambios aplicados.
     */
    public TematicaEntity update(TematicaEntity tematicaEntity) {
        LOGGER.log(Level.INFO, "Actualizando tematica con id = {0}", tematicaEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar la tematica con id = {0}", tematicaEntity.getId());
        return em.merge(tematicaEntity);
    }
    
    /**
     * Borra una tematica de la base de datos recibiendo como argumento el id
     * de la tematica
     *
     * @param tematicasId: id correspondiente a la tematica a borrar.
     */
    public void delete(Long tematicasId) {
        LOGGER.log(Level.INFO, "Borrando tematica con id = {0}", tematicasId);
        
        TematicaEntity entity = em.find(TematicaEntity.class, tematicasId);
        
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la tematica con id = {0}", tematicasId);
    }
    
    /**
     * Busca si hay alguna tematica con el nombre que se envía de argumento
     *
     * @param name: Nombre de la tematica que se está buscando
     * @return null si no existe ninguna tematica con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public TematicaEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando tematica por nombre ", name);
        // Se crea un query para buscar tematicaes con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From TematicaEntity e where e.name = :name", TematicaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<TematicaEntity> sameName = query.getResultList();
        TematicaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar tematica por nombre ", name);
        return result;
    }
}
