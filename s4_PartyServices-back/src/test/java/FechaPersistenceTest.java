
import co.edu.uniandes.csw.partyServices.persistence.FechaPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class FechaPersistenceTest {
    @Inject
    private FechaPersistence fechaPersistence;
    
    @PersistenceContext
    private EntityManager em;
}
