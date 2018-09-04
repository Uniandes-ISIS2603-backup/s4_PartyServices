/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.SugerenciaEntity;
import java.util.List;
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
    
    @PersistenceContext(unitName="LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la base de datos.
     * 
     * @param sugerenciaEntity objeto sugerencia que se creará en la base de datos.
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SugerenciaEntity create(SugerenciaEntity sugerenciaEntity){
        em.persist(sugerenciaEntity);
        return sugerenciaEntity;
    }
    
    /**
     * Devuelve todas las sugerencias de la base de datos.
     *
     * @return una lista con todas las sugerencias que encuentre en la base de
     * datos, "select u from SugerenciaEntity u" es como un "select * from
     * SugerenciaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<SugerenciaEntity> findAll() {
      
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = em.createQuery("select u from EditorialEntity u", SugerenciaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de sugerencias.
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna sugerencia con el id que se envía de argumento
     *
     * @param sugerenciaId: id correspondiente a la sugerencia buscada.
     * @return una sugerencia.
     */
    public SugerenciaEntity find(Long sugerenciaId) {
        
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(SugerenciaEntity.class, sugerenciaId);
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
        
        return em.merge(sugerenciaEntity);
    }
    
    /**
     *
     * Borra una sugerencia de la base de datos recibiendo como argumento el id
     * de la sugerencia
     *
     * @param sugerenciaId: id correspondiente a la sugerencia a borrar.
     */
    public void delete(Long sugerenciaId) {
        
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        SugerenciaEntity entity = em.find(SugerenciaEntity.class, sugerenciaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        
    }
}
