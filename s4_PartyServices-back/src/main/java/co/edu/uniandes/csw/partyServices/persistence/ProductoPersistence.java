/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
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
public class ProductoPersistence 
{
    
     private static final Logger LOGGER = Logger.getLogger(ProductoPersistence.class.getName()) ;
     
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em ;
    
    
    public ProductoEntity create(ProductoEntity pProductoEntity)
    { 
        LOGGER.log(Level.INFO, "Creando un nuevo producto");
        
        em.persist(pProductoEntity);
        
        LOGGER.log(Level.INFO, "Saliendo de crear un producto");
        
        return pProductoEntity ;
          
    }
    
    
    public List<ProductoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los productos");
        
        TypedQuery query = em.createQuery("select u from ProductoEntity u", ProductoEntity.class);
        
        return query.getResultList();
           
    }
    
    
    public ProductoEntity find(Long pProductoId)
    {
        
        LOGGER.log(Level.INFO, "Buscando el producto solicitado");
        
        return em.find(ProductoEntity.class, pProductoId) ;
        
    }
    
    
    public void delete(Long pProductoId)
    {
        LOGGER.log(Level.INFO, "Borrando producto solicitado");
        
        ProductoEntity entity = find(pProductoId) ;
        
        em.remove(entity);
        
        
        LOGGER.log(Level.INFO, "Saliendo de borrar el producto solicitado");
        
    }
    
       public ProductoEntity update(ProductoEntity pProductoEntity) 
       {
           
        LOGGER.log(Level.INFO, "Actualizando el producto solicitado");
  
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar el producto solicitado");
        
        
        return em.merge(pProductoEntity);
        
        
       }
    
    
}
