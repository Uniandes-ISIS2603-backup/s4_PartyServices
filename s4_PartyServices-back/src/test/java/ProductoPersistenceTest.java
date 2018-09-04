
import co.edu.uniandes.csw.partyServices.entities.EventoEntity;
import co.edu.uniandes.csw.partyServices.entities.ProductoEntity;
import co.edu.uniandes.csw.partyServices.persistence.EventoPersistence;
import co.edu.uniandes.csw.partyServices.persistence.ProductoPersistence;
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
public class ProductoPersistenceTest 
{
    
    
    @Inject
    private ProductoPersistence productoPersistence ;
        
    @PersistenceContext
    private EntityManager em ;
    
    @Inject
    UserTransaction utx ;
       
    private List<ProductoEntity> data = new ArrayList<ProductoEntity>() ;
    
     @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProductoEntity.class.getPackage())
                .addPackage(ProductoPersistence.class.getPackage())
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
        em.createQuery("delete from ProductoEntity").executeUpdate();
    }

    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ProductoEntity entity = factory.manufacturePojo(ProductoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    @Test 
    public void createProductoTest()
    {
         PodamFactory factory = new PodamFactoryImpl();
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        ProductoEntity result = productoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ProductoEntity entity = em.find(ProductoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    
    @Test
    public void deleteProductoTest()
    {
        ProductoEntity entity = data.get(0);
        productoPersistence.delete(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
     
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
