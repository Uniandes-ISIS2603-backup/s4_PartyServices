/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.NotificacionEntity;
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
public class NotificacionPersistence {
    private static final Logger LOGGER = Logger.getLogger(NotificacionPersistence.class.getName());
    
    @PersistenceContext (unitName = "LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param NotificacionEntity objeto notificación que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */

    public NotificacionEntity create (NotificacionEntity notificacionEntity){
        LOGGER.log(Level.INFO, "Creando una nueva notificación");
                em.persist(notificacionEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una nueva notificación");
        return notificacionEntity;
    }
    
    /**
     * Devuelve todos las notificaciones de la base de datos.
     *
     * @return una lista con todas las notificaciones que encuentre en la base de
     * datos, "select u from NotificaciónEntity u" es como un "select * from
     * ProveedorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<NotificacionEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las notificación");
        // Se crea un query para buscar todas las notificaciones en la base de datos.
        TypedQuery query = em.createQuery("select u from NotificacionEntity u", NotificacionEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de notificaciones.
        return query.getResultList();
    }
    
	    /**
     * Busca si hay alguna notificacion con el id que se envía de argumento
     *
     * @param notifID: id correspondiente a la notificación buscada.
     * @return una notificacion.
     */
    public NotificacionEntity find(Long notifID) {
        LOGGER.log(Level.INFO, "Consultando notificación con id={0}", notifID);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from NotificaciónEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(NotificacionEntity.class, notifID);
    }

	 /**
     * Actualiza una notificacion.
     *
     * @param notificacionEntity: la notificación que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una notificación con los cambios aplicados.
     */
    public NotificacionEntity update(NotificacionEntity notificacionEntity) {
        LOGGER.log(Level.INFO, "Actualizando notificación con id = {0}", notificacionEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la notificación con id = {0}", notificacionEntity.getId());
        return em.merge(notificacionEntity);
    }
	
    /**
     *
     * Borra una notificación de la base de datos recibiendo como argumento el id
     * de la notificación
     *
     * @param notifID: id correspondiente a la notificación a borrar.
     */
    public void delete(Long notifID) {
        LOGGER.log(Level.INFO, "Borrando notificación con id = {0}", notifID);
        // Se hace uso de mismo método que esta explicado en public NotificaciónEntity find(Long id) para obtener la notificación a borrar.
        NotificacionEntity entity = em.find(NotificacionEntity.class, notifID);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from NotificaciónEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la notificación con id = {0}", notifID);
    }
	
    /**
     * Busca si hay alguna notificación con el nombre que se envía de argumento
     *
     * @param name: Nombre de la notificación que se está buscando
     * @return null si no existe ninguna notificación con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    /*public NotificacionEntity findByName(Long name) {
        LOGGER.log(Level.INFO, "Consultando notificación por nombre ", name);
        // Se crea un query para buscar notificaciones con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From NotificaciónEntity e where e.name = :name", NotificacionEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<NotificacionEntity> sameName = query.getResultList();
        NotificacionEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar notificación por nombre ", name);
        return result;
    }*/
}
