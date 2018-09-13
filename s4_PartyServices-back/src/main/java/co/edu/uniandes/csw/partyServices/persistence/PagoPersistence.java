/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
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
public class PagoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(PagoPersistence.class.getName()) ;
    
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em ;
    
    
    public PagoEntity create(PagoEntity pPagoEntity)
    { 
        LOGGER.log(Level.INFO, "Se está creando un nuevo pago");
        
        em.persist(pPagoEntity);
        
        LOGGER.log(Level.INFO, "Se creó un nuevo pago");
        
        return pPagoEntity ;
          
    }
    
    
    public List<PagoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Se busca todos los pagos");
        
        TypedQuery query = em.createQuery("select u from PagoEntity u", PagoEntity.class);
        
        return query.getResultList();
           
    }
    
    
    public PagoEntity find(Long pPagoId)
    {
        
        LOGGER.log(Level.INFO, "Se busca el pago en base al ID solicitado");
        
        return em.find(PagoEntity.class, pPagoId) ;
        
    }
    
    
    public void delete(Long pPagoId)
    {
        LOGGER.log(Level.INFO, "Se borra al pago en base del Id solicitado");
        
        PagoEntity entityPago= find(pPagoId) ;
        
        em.remove(entityPago);
        
        
        LOGGER.log(Level.INFO, "Se ha borrado al pago en base del Id solicitado");
        
    }
    
    public PagoEntity update(PagoEntity pagoEntity) 
       {
           
        LOGGER.log(Level.INFO, "Se actualiza el pago solicitado");
  
        LOGGER.log(Level.INFO, "Se ha actualizado el pago solicitado");
        return em.merge(pagoEntity);
        

       }
    
    public PagoEntity findByUsuario(String pUsuario) 
    {
        LOGGER.log(Level.INFO, "Se consulta por el nombre ", pUsuario);
        TypedQuery query = em.createQuery("Select e From PagoEntity e where e.usuario = :usuario", PagoEntity.class);

        query = query.setParameter("usuario", pUsuario);
        
        List<PagoEntity> sameName = query.getResultList();
        PagoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Se han consultado todos los pagos por el nombre ", pUsuario);
        return result;
        
        
    }
	
    
    
        
    
    
    
    
    
    
    
}
