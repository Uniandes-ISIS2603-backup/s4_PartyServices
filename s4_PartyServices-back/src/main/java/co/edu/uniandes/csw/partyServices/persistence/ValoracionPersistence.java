/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ValoracionEntity;
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
public class ValoracionPersistence {
    
    @PersistenceContext(unitName="LosMasmelosPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la base de datos.
     * 
     * @param valoracionEntity objeto valoracion que se creará en la base de datos.
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ValoracionEntity create(ValoracionEntity valoracionEntity){
        em.persist(valoracionEntity);
        return valoracionEntity;
    }
    
    /**
     * Devuelve todas las valoraciones de la base de datos.
     *
     * @return una lista con todas las valoraciones que encuentre en la base de
     * datos, "select u from ValoracionEntity u" es como un "select * from
     * ValroacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ValoracionEntity> findAll() {
      
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = em.createQuery("select u from EditorialEntity u", ValoracionEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de sugerencias.
        return query.getResultList();
    }
    
    /**
     * Busca si hay alguna valoracion con el id que se envía de argumento
     *
     * @param valoracionId: id correspondiente a la valoracion buscada.
     * @return una valoracion.
     */
    public ValoracionEntity fin(Long valoracionId){
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(ValoracionEntity.class, valoracionId);
    }
    
    /**
     * Actualiza una valoracion.
     *
     * @param valoracionEntity: la sugerencia que viene con los nuevos cambios.
     * Por ejemplo el comentario pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una valoracion con los cambios aplicados.
     */
    public ValoracionEntity update(ValoracionEntity valoracionEntity) {
        
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        
        return em.merge(valoracionEntity);
    }
    
    /**
     *
     * Borra una valoracion de la base de datos recibiendo como argumento el id
     * de la valoracion
     *
     * @param valoracionId: id correspondiente a la valoraciob a borrar.
     */
    public void delete(Long valoracionId) {
        
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        ValoracionEntity entity = em.find(ValoracionEntity.class, valoracionId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        
    }
    
}
