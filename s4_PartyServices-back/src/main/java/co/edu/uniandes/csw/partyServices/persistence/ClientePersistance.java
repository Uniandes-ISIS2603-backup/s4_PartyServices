/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
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
public class ClientePersistance {
    
    @PersistenceContext(unitName="LosMasmelosPU")
    protected EntityManager EM;
    
    
    public ClienteEntity create(ClienteEntity clienteEntity){
        EM.persist(clienteEntity);
        return clienteEntity;
    }
    
    
    public List<ClienteEntity> findAll() {
      
        // Se crea un query para buscar todas las editoriales en la base de datos.
        TypedQuery query = EM.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de sugerencias.
        return query.getResultList();
    }
    
    
    public ClienteEntity fin(Long clienteId){
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EditorialEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return EM.find(ClienteEntity.class, clienteId);
    }
    
    
    public ClienteEntity update(ClienteEntity clienteEntity) {
        
        
        
        return EM.merge(clienteEntity);
    }
    
    
    public void delete(Long clienteId) {
        
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        ClienteEntity entity = EM.find(ClienteEntity.class, clienteId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        EM.remove(entity);
        
    }
    
}
