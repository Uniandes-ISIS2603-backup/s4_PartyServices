/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
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
public class EventoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName()) ;
    
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em ;
    
    
    public EventoEntity create(EventoEntity pEventoEntity)
    { 
        LOGGER.log(Level.INFO, "Creando un nuevo evento");
        
        em.persist(pEventoEntity);
        
        LOGGER.log(Level.INFO, "Saliendo de crear un evento");
        
        return pEventoEntity ;
          
    }
    
    
    public List<EventoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los eventos");
        
        TypedQuery query = em.createQuery("select u from EventoEntity u", EventoEntity.class);
        
        return query.getResultList();
           
    }
    
    
    public EventoEntity find(Long pEventoId)
    {
        
        LOGGER.log(Level.INFO, "Buscando el evento solicitado");
        
        return em.find(EventoEntity.class, pEventoId) ;
        
    }
    
    
    public void delete(Long pIdEvento)
    {
        LOGGER.log(Level.INFO, "Borrando evento solicitado");
        
        EventoEntity entity = find(pIdEvento) ;
        
        em.remove(entity);
        
        
        LOGGER.log(Level.INFO, "Saliendo de borrar el evento solicitado");
        
    }
    
    public EventoEntity update(EventoEntity eventoEntity) 
       {
           
        LOGGER.log(Level.INFO, "Actualizando el evento solicitado");
  
        
        LOGGER.log(Level.INFO, "Saliendo de actualizar el evento solicitado");
        return em.merge(eventoEntity);
       }
    /**
     *
     * @param name
     * @return
     */
    public EventoEntity findByName(String nombre) 
    {
        LOGGER.log(Level.INFO, "Consultando evento por nombre ", nombre);
        TypedQuery query = em.createQuery("Select e From EventoEntity e where e.nombre = :nombre", EventoEntity.class);

        query = query.setParameter("nombre", nombre);
        
        List<EventoEntity> sameName = query.getResultList();
        EventoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar evento por nombre ", nombre);
        return result;
        
        
    }
	
    
    
        
    
    
    
    
    
    
    
}
