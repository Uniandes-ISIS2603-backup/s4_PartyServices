
import co.edu.uniandes.csw.partyServices.entities.PagoEntity;
import co.edu.uniandes.csw.partyServices.persistence.PagoPersistence;
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
public class PagoPersistenceTest 
{
    @Inject
    private PagoPersistence pagoPersistence ;
    
    
    @PersistenceContext
    private EntityManager em ;
    
    @Inject
    UserTransaction utx ;
    
    
    
    private List<PagoEntity> data = new ArrayList<PagoEntity>() ;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    
    @Test 
    public void createPagoTest()
    {
         PodamFactory factory = new PodamFactoryImpl();
       PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        PagoEntity result = pagoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        PagoEntity entity = em.find(PagoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
    }
    
    
    @Test
    public void deletePagoTest()
    {
        PagoEntity entity = data.get(0);
        pagoPersistence.delete(entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());
        Assert.assertNull(deleted);
     
        
    }
    
     @Test
    public void FindPagoByNameTest() {
        PagoEntity entity = data.get(0);
        PagoEntity newEntity = pagoPersistence.findByName(entity.getUsuario());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getUsuario(), newEntity.getUsuario());
    }
    
    
}
