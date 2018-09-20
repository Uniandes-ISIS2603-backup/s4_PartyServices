/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jesús Orlando Cárcamo Posada
 */
@Stateless
public class SugerenciaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(SugerenciaPersistence.class.getName());
    
    @PersistenceContext(unitName="LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la base de datos.
     * 
     * @param sugerenciaEntity objeto sugerencia que se creará en la base de datos.
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SugerenciaEntity create(SugerenciaEntity sugerenciaEntity){
        LOGGER.log(Level.INFO, "Creando una sugerencia nueva");
        em.persist(sugerenciaEntity);
        LOGGER.log(Level.INFO, "Sugerencia creada");
        return sugerenciaEntity;
    }
    
    /**
     * Actualiza una sugerencia.
     *
     * @param sugerenciaEntity: la sugerencia que viene con los nuevos cambios.
     * Por ejemplo el comentario pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una sugerencia con los cambios aplicados.
     */
    public SugerenciaEntity update(SugerenciaEntity sugerenciaEntity) {
        
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Actualizando sugerencia con id = {0}", sugerenciaEntity.getId());
        return em.merge(sugerenciaEntity);
    }
    
    /**
     *
     * Borra una sugerencia de la base de datos recibiendo como argumento el id
     * de la sugerencia
     *
     * @param sugerenciasId: id correspondiente a la sugerencia a borrar.
     */
    public void delete(Long sugerenciasId) {
        LOGGER.log(Level.INFO, "Borrando sugerencia con id = {0}", sugerenciasId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        SugerenciaEntity entity = em.find(SugerenciaEntity.class, sugerenciasId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la sugerencia con id = {0}", sugerenciasId);
        
    }
    
    /**
     * Buscar una sugerencia
     *
     * Busca si hay alguna sugerencia asociada a una tematica y con un ID específico.
     *
     * @param tematicasId El ID de la tematica con respecto a la cual se busca.
     * @param sugerenciasId El ID de la sugerencia buscada.
     * @return La sugerenica encontrada o null. Nota: Si existe una o más sugerencias
     * devuelve siempre la primera que encuentra.
     */
    public SugerenciaEntity find(Long tematicasId, Long sugerenciasId) {
        
        LOGGER.log(Level.INFO, "Consultando la sugerencia con id = {0} de la temática con id = " + tematicasId, sugerenciasId);
        TypedQuery<SugerenciaEntity> q = em.createQuery("select p from SugerenciaEntity p where (p.tematica.id = :tematicasid) and (p.id = :sugerenciasId)", SugerenciaEntity.class);
        q.setParameter("tematicasid", tematicasId);
        q.setParameter("sugerenciasId", sugerenciasId);
        List<SugerenciaEntity> results = q.getResultList();
        SugerenciaEntity sugerencia = null;
        if (results == null) {
            sugerencia = null;
        } else if (results.isEmpty()) {
            sugerencia = null;
        } else if (results.size() >= 1) {
            sugerencia = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la sugerencia con id = {0} de la temática con id =" + tematicasId, sugerenciasId);
        return sugerencia;
    }   
    
}
