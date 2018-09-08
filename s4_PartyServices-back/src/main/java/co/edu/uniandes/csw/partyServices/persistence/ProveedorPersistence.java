/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ProveedorEntity;
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
public class ProveedorPersistence {
    private static final Logger LOGGER = Logger.getLogger(ProveedorPersistence.class.getName());
    
    @PersistenceContext (unitName = "LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param proveedorEntity objeto proveedor que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */

    public ProveedorEntity create (ProveedorEntity proveedorEntity){
        LOGGER.log(Level.INFO, "Creando un nuevo proveedor");
                em.persist(proveedorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo proveedor");
        return proveedorEntity;
    }
    
    /**
     * Devuelve todos los proveedores de la base de datos.
     *
     * @return una lista con todos los proveedores que encuentre en la base de
     * datos, "select u from ProveedorEntity u" es como un "select * from
     * ProveedorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ProveedorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los proveedores");
        // Se crea un query para buscar todos los proveedores en la base de datos.
        TypedQuery query = em.createQuery("select u from ProveedorEntity u", ProveedorEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de proveedores.
        return query.getResultList();
    }
    
	    /**
     * Busca si hay algun proveedor con el id que se envía de argumento
     *
     * @param proveedorID: id correspondiente al proveedor buscado.
     * @return un proveedor.
     */
    public ProveedorEntity find(Long proveedorID) {
        LOGGER.log(Level.INFO, "Consultando proveedor con id={0}", proveedorID);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(ProveedorEntity.class, proveedorID);
    }

	 /**
     * Actualiza un proveedor.
     *
     * @param ProveedorEntity: el proveedor que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un proveedor con los cambios aplicados.
     */
    public ProveedorEntity update(ProveedorEntity proveedorEntity) {
        LOGGER.log(Level.INFO, "Actualizando proveedor con id = {0}", proveedorEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el proveedor con id = {0}", proveedorEntity.getId());
        return em.merge(proveedorEntity);
    }
	
    /**
     *
     * Borra un proveedor de la base de datos recibiendo como argumento el id
     * del proveedor
     *
     * @param proveedoresID: id correspondiente al proveedor a borrar.
     */
    public void delete(Long proveedoresID) {
        LOGGER.log(Level.INFO, "Borrando proveedor con id = {0}", proveedoresID);
        // Se hace uso de mismo método que esta explicado en public ProveedorEntity find(Long id) para obtener el proveedor a borrar.
        ProveedorEntity entity = em.find(ProveedorEntity.class, proveedoresID);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from ProveedorEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el proveedor con id = {0}", proveedoresID);
    }
	
    /**
     * Busca si hay algun proveedor con el nombre que se envía de argumento
     *
     * @param name: Nombre del proveedor que se está buscando
     * @return null si no existe ningún editorial con el nombre del argumento.
     * Si existe alguno devuelve el primero.
     */
    public ProveedorEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando proveedor por nombre ", name);
        // Se crea un query para buscar editoriales con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.name = :name", ProveedorEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<ProveedorEntity> sameName = query.getResultList();
        ProveedorEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar proveedor por nombre ", name);
        return result;
    }
}
