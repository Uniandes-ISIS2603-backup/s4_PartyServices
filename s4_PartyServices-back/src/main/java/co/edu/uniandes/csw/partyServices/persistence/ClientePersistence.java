/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
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
public class ClientePersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName()) ;
    
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em ;
    
    
    public ClienteEntity create(ClienteEntity pClienteEntity)
    { 
        LOGGER.log(Level.INFO, "Se está creando un nuevo cliente");
        
        em.persist(pClienteEntity);
        
        LOGGER.log(Level.INFO, "Se creó un nuevo cliente");
        
        return pClienteEntity ;
          
    }
    
    
    public List<ClienteEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Se busca todos los clientes");
        
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        
        return query.getResultList();
           
    }
    
    
    public ClienteEntity find(Long pClienteId)
    {
        
        LOGGER.log(Level.INFO, "Se busca el cliente en base al ID solicitado");
        
        return em.find(ClienteEntity.class, pClienteId) ;
        
    }
    
    
    public void delete(Long pClienteId)
    {
        LOGGER.log(Level.INFO, "Se borra al cliente en base del Id solicitado");
        
        ClienteEntity entityCliente = find(pClienteId) ;
        
        em.remove(entityCliente);
        
        
        LOGGER.log(Level.INFO, "Se ha borrado al cliente en base del Id solicitado");
        
    }
    
    public ClienteEntity update(ClienteEntity clienteEntity) 
       {
           
        LOGGER.log(Level.INFO, "Se actualiza el cliente solicitado");
  
        LOGGER.log(Level.INFO, "Se ha actualizado el cliente solicitado");
        return em.merge(clienteEntity);
        

       }
    
    public ClienteEntity findByName(String name) 
    {
        LOGGER.log(Level.INFO, "Se consulta por el nombre ", name);
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.usuario = :usuario", ClienteEntity.class);

        query = query.setParameter("usuario", name);
        
        List<ClienteEntity> sameName = query.getResultList();
        ClienteEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Se han consultado todos los clientes por el nombre ", name);
        return result;
        
        
    }
	
    
    
        
    
    
    
    
    
    
    
}
