/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.partyServices.persistence;

import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.FechaEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Hernandez
 */
public class FechaEventoPersistence {
    private static final Logger LOGGER = Logger.getLogger(FechaEventoPersistence.class.getName());
    @PersistenceContext(unitName = "LosMasmelosPU")
    protected EntityManager em;
    
    public void anadirEventoAFecha(long idFecha, long idEvento)
    {
        LOGGER.log(Level.INFO,"Anadiendo un evento a la fecha {0}",idFecha);
        //Persiste el elemento 
        Query query = em.createQuery(
                "INSERT INTO EVENTOENTITY_FECHAENTITY (EVENTOS_ID,FECHAS_ID) VALUES (idEvento,idFecha)"
        );
        query = query.setParameter("idFecha", idFecha);
        query = query.setParameter("idEvento", idEvento);
        query.executeUpdate();   
    }
}
