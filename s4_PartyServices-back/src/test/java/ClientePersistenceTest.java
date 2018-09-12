
import co.edu.uniandes.csw.partyServices.entities.ClienteEntity;
import co.edu.uniandes.csw.partyServices.persistence.ClientePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

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
public class ClientePersistenceTest 
{
    @Inject
    private ClientePersistence clientePersistence ;
    
    
    @PersistenceContext
    private EntityManager em ;
    
    @Inject
    UserTransaction utx ;
    
    
    
    private List<ClienteEntity> data = new ArrayList<ClienteEntity>() ;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
       @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

   
    private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    
    @Test 
    public void createClienteTest()
    {
         PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(newEntity);

        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());

        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
    }
    
    
    @Test
    public void deleteClienteTest()
    {
        ClienteEntity entity = data.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
     
        
    }
    
     @Test
    public void FindClienteByNameTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.findByName(entity.getLogin());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getLogin(), newEntity.getLogin());
    }
    
    
}
