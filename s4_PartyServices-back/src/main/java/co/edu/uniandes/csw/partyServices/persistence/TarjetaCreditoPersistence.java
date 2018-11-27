/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.TarjetaCreditoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jesús Orlando Cárcamo Posada.
 */
@Stateless
public class TarjetaCreditoPersistence {

    private static final Logger LOGGER = Logger.getLogger(TarjetaCreditoPersistence.class.getName());

    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;

    public TarjetaCreditoEntity create(TarjetaCreditoEntity tarjetaCreditoEntity) {
        LOGGER.log(Level.INFO, "Creando una tarjeta de credito nueva");
        em.persist(tarjetaCreditoEntity);
        LOGGER.log(Level.INFO, "Tarjeta de credito creada");
        return tarjetaCreditoEntity;
    }
    
    public TarjetaCreditoEntity update(TarjetaCreditoEntity tarjetaCreditoEntity) {

        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la editorial con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Actualizando tarjeta de credito con id = {0}", tarjetaCreditoEntity.getId());
        return em.merge(tarjetaCreditoEntity);
    }
    
    public void delete(Long tarjetaCreditoId) {
        LOGGER.log(Level.INFO, "Borrando la tarjeta de credito con id = {0}", tarjetaCreditoId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        TarjetaCreditoEntity entity = em.find(TarjetaCreditoEntity.class, tarjetaCreditoId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la tarjeta de credito con id = {0}", tarjetaCreditoId);
    }
    
    public TarjetaCreditoEntity find(Long clientesId, Long tarjetaCreditoId) {

        LOGGER.log(Level.INFO, "Consultando la tarjeta de credito con id = {0} del cliente con id = {1}", new Object[]{tarjetaCreditoId, clientesId});
        TypedQuery<TarjetaCreditoEntity> q = em.createQuery("select p from TarjetaCreditoEntity p where (p.cliente.id = :clientesId) and (p.id = :tarjetaCreditoId)", TarjetaCreditoEntity.class);
        q.setParameter("clientesId", clientesId);
        q.setParameter("tarjetaCreditoId", tarjetaCreditoId);
        List<TarjetaCreditoEntity> results = q.getResultList();
        TarjetaCreditoEntity tarjetaCredito = null;
        if (!results.isEmpty()) {
            tarjetaCredito = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la tarjeta de credito = {0} del cliente con id =" + clientesId, tarjetaCreditoId);
        return tarjetaCredito;
    }
    
    public List<TarjetaCreditoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las tarjetas de credito");
        TypedQuery query = em.createQuery("select u from TarjetaCreditoEntity u", TarjetaCreditoEntity.class);
        return query.getResultList();
    }
}
