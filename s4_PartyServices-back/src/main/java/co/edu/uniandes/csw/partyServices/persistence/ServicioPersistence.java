/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ServicioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Servicio. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Tomas Vargas
 */
@Stateless
public class ServicioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioPersistence.class.getName());

    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param servicioEntity objeto servicio que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ServicioEntity create(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Creando una servicio nueva");
        em.persist(servicioEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una servicio nueva");
        return servicioEntity;
    }
    
    /**
     * Devuelve todas las servicios de la base de datos.
     *
     * @return una lista con todas las servicios que encuentre en la base de
     * datos, "select u from ServicioEntity u" es como un "select * from
     * ServicioEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ServicioEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los servicios");
        
        TypedQuery query = em.createQuery("select u from ServicioEntity u", ServicioEntity.class);
        
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna servicio con el id que se envía de argumento
     *
     * @param serviciosId: id correspondiente a la servicio buscada.
     * @return una servicio.
     */
    public ServicioEntity find(Long serviciosId) {
        LOGGER.log(Level.INFO, "Consultando servicio con id={0}", serviciosId);
        
        return em.find(ServicioEntity.class, serviciosId);
    }
    
    /**
     * Actualiza una servicio.
     *
     * @param servicioEntity: la servicio que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una servicio con los cambios aplicados.
     */
    public ServicioEntity update(ServicioEntity servicioEntity) {
        LOGGER.log(Level.INFO, "Actualizando servicio con id = {0}", servicioEntity.getId());
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar la servicio con id = {0}", servicioEntity.getId());
        return em.merge(servicioEntity);
    }
    
    /**
     * Borra una servicio de la base de datos recibiendo como argumento el id
     * de la servicio
     *
     * @param serviciosId: id correspondiente a la servicio a borrar.
     */
    public void delete(Long serviciosId) {
        LOGGER.log(Level.INFO, "Borrando servicio con id = {0}", serviciosId);
        
        ServicioEntity entity = em.find(ServicioEntity.class, serviciosId);
        
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el servicio con id = {0}", serviciosId);
    }
}
